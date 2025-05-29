package com.wan.ekyc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.ekyc.common.DateUtil;
import com.wan.ekyc.common.EkycUtil;
import com.wan.ekyc.common.FileUtil;
import com.wan.ekyc.common.ImageUtil;
import com.wan.ekyc.dto.ekyc.EkycRequest;
import com.wan.ekyc.dto.ekyc.OkIdFields;
import com.wan.ekyc.dto.innovative.*;
import com.wan.ekyc.model.EkycAudit;
import com.wan.ekyc.model.User;
import com.wan.ekyc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.wan.ekyc.common.Constant.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EkycService {
    private final UserRepository userRepo;
    private final InnovativeService innovativeService;
    private final EkycAuditService ekycAuditService;
    private final EkycDocumentService ekycDocumentService;
    private final EkycThresholdService ekycThresholdService;

    private final String EkycFilePath = "src/main/resources/static/ekyc.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public User initEkyc(Long userId, EkycRequest req) {
        // load sample image if not they are not provided
        loadSampleImage(req);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", userId);
                    return new RuntimeException();
                });

        if (!user.getStatus().equals(PENDING_EKYC)) {
            log.error("User {} has not yet finished onboarding process", user.getId());
            throw new RuntimeException();
        }

        // Each user will get journey id only once, at this point journey id should be null
        if (user.getJourneyId() != null) {
            log.error("User {} has already got ekyc journey id", user.getId());
            throw new RuntimeException();
        }

        CreateJourneyIdResponse response = innovativeService.createJourneyId();
        if (response == null || !response.getStatus().equals("success")) {
            log.error("CreateJourneyId API returned failed status: {}", response != null ? response.getStatus() : "");
            throw new RuntimeException();
        }

        String journeyId = response.getJourneyId();

        user.setStatus(PENDING_EKYC_REVIEW);
        user.setJourneyId(journeyId);
        user = userRepo.save(user);

        // In a real situation, this is the point where we queue ekyc processing to message broker like Kafka
        // For simplicity, we just save to local file and processEkyc will be initiated through rest call
        req.setJourneyId(journeyId);
        createEkycRequest(req);

        ekycAuditService.saveOrUpdate(journeyId, CREATE_JOURNEY, SUCCESS, null);

        return user;
    }

    public User processEkyc(String journeyId) {
        User user = userRepo.findByJourneyId(journeyId)
                .orElseThrow(() -> {
                    log.error("User with journeyId {} not found", journeyId);
                    return new RuntimeException();
                });

        if (!user.getStatus().equals(PENDING_EKYC_REVIEW)) {
            log.error("User {} has not yet finished eKYC initiation process", user.getId());
            throw new RuntimeException();
        }

        EkycRequest ekycRequest = getEkycRequest();
        if (ekycRequest == null) {
            log.error("Ekyc request is null");
            throw new RuntimeException();
        }

        log.info("Processing OkId");
        boolean okIdSuccess = processOkId(ekycRequest, user);

        if (okIdSuccess) {
            log.info("Processing OkDoc");
            boolean okDocSuccess = processOkDoc(ekycRequest);

            log.info("Processing OkFace");
            boolean okFaceSuccess = processOkFace(ekycRequest);

            log.info("Processing OkLive");
            boolean okLiveSuccess = processOkLive(ekycRequest);

            if (okLiveSuccess && okFaceSuccess && okDocSuccess) {
                user.setStatus(COMPLETED_EKYC);
                user = userRepo.save(user);

                completeJourney(journeyId);

                log.info("eKYC completed");
            }
        }

        return user;
    }

    public List<EkycAudit> getAudits(String journeyId) {
        return ekycAuditService.getAll(journeyId);
    }

    private boolean processOkId(EkycRequest request, User user) {
        String category = request.getCategory();
        String journeyId = request.getJourneyId();
        String frontId = request.getFrontId();
        String backId = request.getBackId();
        String passport = request.getPassport();

        OkIdResponse response;

        ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, IN_PROGRESS, null);

        if (category.equals(DOC_NON_PASSPORT)) {
            response = innovativeService.initOkId(journeyId, frontId, backId, true);
        } else if(category.equals(DOC_PASSPORT)) {
            response = innovativeService.initOkId(journeyId, passport, null, false);
        } else {
            log.error("Invalid document category: {}", category);
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Invalid document category");
            return false;
        }

        if (response == null || !response.getStatus().equals("success")) {
            log.error("OkayId API returned failed status: {}", response != null ? response.getStatus() : "");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "OkayId API returned failed status");
            return false;
        }

        return evaluateOkId(journeyId, category, user, response);
    }

    private boolean evaluateOkId(String journeyId, String category, User user, OkIdResponse response) {
        String docType = ekycDocumentService.getDocumentType(category, response.getDocumentType());
        if (docType == null || docType.isEmpty()) {
            if (!EkycUtil.isPassport(response.getDocumentType())) {
                log.error("Failed to get document type, category: {}, type: {}", category, response.getDocumentType());
                ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "OkayId API returned failed status");
                return false;
            }
        }

        OkIdFields fields = EkycUtil.translateOkIdFields(response);

        if (fields.getDateOfExpiry() != null) {
            String format = "MM/dd/yyyy"; // 11/24/2032
            LocalDate expDate = DateUtil.parseDate(fields.getDateOfExpiry(), format);
            LocalDate today = LocalDate.now();
            if (expDate.isBefore(today)) {
                log.error("Document expired");
                ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Document expired");
                return false;
            }
        }

        if (fields.getSurnameAndGivenName() == null || fields.getSurnameAndGivenName().isEmpty()) {
            log.error("Failed to get fullName from OkayID API");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Failed to get fullName");
            return false;
        }

        if (fields.getDocumentNumber() == null || fields.getDocumentNumber().isEmpty()) {
            log.error("Failed to get document number from OkayID API");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Failed to get document number");
            return false;
        }

        if (fields.getSex() == null || fields.getSex().isEmpty()) {
            log.error("Failed to get sex from OkayID API");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Failed to get sex");
            return false;
        }

        if (fields.getAddress1() == null || fields.getAddress1().isEmpty()) {
            log.error("Failed to get address1 from OkayID API");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Failed to get address1");
            return false;
        }

        if (fields.getIssuingStateName() == null || fields.getIssuingStateName().isEmpty()) {
            log.error("Failed to get issuingStateName from OkayID API");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Failed to get issuingStateName");
            return false;
        }

        user.setFullName(fields.getSurnameAndGivenName());
        user.setIdType(docType);
        user.setIdNo(fields.getDocumentNumber());
        user.setGender(fields.getSex());
        user.setAddress1(fields.getAddress1());
        user.setAddress2(fields.getAddress2());
        user.setAddress3(fields.getAddress3());
        user.setCountry(fields.getIssuingStateName());
        userRepo.save(user);

        ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, SUCCESS, "");

        return true;
    }

    private boolean processOkDoc(EkycRequest request) {
        String category = request.getCategory();
        String journeyId = request.getJourneyId();
        String frontId = request.getFrontId();
        String passport = request.getPassport();

        OkDocResponse response;

        ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, IN_PROGRESS, null);

        if (category.equals(DOC_NON_PASSPORT)) {
            response = innovativeService.initOkDoc(journeyId, frontId, true);
        } else if(category.equals(DOC_PASSPORT)) {
            response = innovativeService.initOkDoc(journeyId, passport, false);
        } else {
            log.error("Invalid document category: {}", category);
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "Invalid document category");
            return false;
        }

        if (response == null || !response.getStatus().equals("success")) {
            log.error("OkayDoc API returned failed status: {}", response != null ? response.getStatus() : "");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "OkayDoc API returned failed status");
            return false;
        }

        return evaluateOkDoc(journeyId, response);
    }

    private boolean evaluateOkDoc(String journeyId, OkDocResponse response) {
        Map<String, String> general = EkycUtil.getGeneralResult(response);
        Map<String, String> generalThresholds = ekycThresholdService.getGeneralThreshold();

        int failedGeneral = 0;
        log.debug("Evaluating OkDoc general landmarks");
        for (Map.Entry<String, String> g : general.entrySet()) {
            String landmark = g.getKey();
            String actual = g.getValue();
            String expected = generalThresholds.get(g.getKey());
            boolean matched = actual.equals(expected);
            log.debug("Landmark: {}, Expected: {}, Actual: {}", landmark, expected, actual);
            if (!matched) {
                failedGeneral++;
            }
        }
        log.info("{}/{} failed \n", failedGeneral, general.size());

        Map<String, BigDecimal> score = EkycUtil.getLandmarkScore(response);
        Map<String, BigDecimal> okDocThreshold = ekycThresholdService.getOkDocThreshold();

        int failedScore = 0;
        log.debug("Evaluating OkDoc score landmarks");
        for (Map.Entry<String, BigDecimal> s : score.entrySet()) {
            String landmark = s.getKey();
            BigDecimal actual = s.getValue();
            BigDecimal expected = okDocThreshold.get(s.getKey());
            log.debug("Landmark: {}, Expected Score: {}, Actual Score: {}", landmark, expected, actual);
            if (expected.compareTo(actual) > 0) { // expected is greater than actual
                failedScore++;
            }
        }
        log.info("{}/{} failed \n", failedScore, score.size());

        if (failedGeneral != 0 || failedScore != 0) {
            if (failedGeneral != 0) {
                ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "Failed general landmark checks");
            }

            if (failedScore == 0) {
                ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "Failed landmark score checks");
            }

            return false;
        }

        ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, SUCCESS, "");

        return true;
    }

    private boolean processOkFace(EkycRequest request) {
        String journeyId = request.getJourneyId();
        String frontId = request.getFrontId();
        String selfie = request.getSelfie();

        ekycAuditService.saveOrUpdate(journeyId, OKAY_FACE, IN_PROGRESS, null);

        OkFaceResponse response = innovativeService.initOkFace(journeyId, frontId, selfie);
        if (response == null || !response.getStatus().equals("success")) {
            log.error("OkayFace API returned failed status: {}", response != null ? response.getStatus() : "");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_FACE, FAILED, "OkayFace API returned failed status");
            return false;
        }

        Map<String, BigDecimal> thresholds = ekycThresholdService.getOkFaceThreshold();

        int failed = 0;
        log.debug("Evaluating OkFace landmarks");
        String landmark = "confidence";
        BigDecimal actual = response.getResultIdCard().getConfidence();
        BigDecimal expected = thresholds.get("confidence");
        log.debug("Landmark: {}, Expected Score: {}, Actual Score: {}", landmark, expected, actual);
        if (expected.compareTo(actual) > 0) { // expected is greater than actual
            failed++;
        }
        log.info("{}/{} failed \n", failed, thresholds.size());

        if (failed != 0) {
            ekycAuditService.saveOrUpdate(journeyId, OKAY_FACE, FAILED, "Failed face verification confidence check");
            return false;
        }

        ekycAuditService.saveOrUpdate(journeyId, OKAY_FACE, SUCCESS, "");

        return true;
    }

    private boolean processOkLive(EkycRequest request) {
        String journeyId = request.getJourneyId();
        String selfie = request.getSelfie();

        ekycAuditService.saveOrUpdate(journeyId, OKAY_LIVE, IN_PROGRESS, null);

        OkLiveResponse response = innovativeService.initOkLive(journeyId, selfie);
        if (response == null || !response.getStatus().equals("success")) {
            log.error("OkayLive API returned failed status: {}", response != null ? response.getStatus() : "");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_LIVE, FAILED, "OkayLive API returned failed status");
            return false;
        }

        Map<String, BigDecimal> thresholds = ekycThresholdService.getOkLiveThreshold();
        Map<String, BigDecimal> results = new HashMap<>();
        results.put("probability", response.getProbability().multiply(new BigDecimal(100)));
        results.put("quality", response.getQuality().multiply(new BigDecimal(100)));

        int failed = 0;
        log.debug("Evaluating OkLive landmarks");
        for (Map.Entry<String, BigDecimal> r : results.entrySet()) {
            String landmark = r.getKey();
            BigDecimal actual = r.getValue();
            BigDecimal expected = thresholds.get(landmark);
            log.debug("Landmark: {}, Expected Score: {}, Actual Score: {}", landmark, expected, actual);
            if (expected.compareTo(actual) > 0) { // expected is greater than actual
                failed++;
            }
        }

        log.info("{}/{} failed \n", failed, thresholds.size());
        if (failed != 0) {
            ekycAuditService.saveOrUpdate(journeyId, OKAY_LIVE, FAILED, "Failed landmark score checks");
            return false;
        }

        ekycAuditService.saveOrUpdate(journeyId, OKAY_LIVE, SUCCESS, "");

        return true;
    }

    public void completeJourney(String journeyId) {
        CompleteJourneyResponse response = innovativeService.completeJourney(journeyId);
        if (response == null || !response.getStatus().equals("success")) {
            log.error("CompleteJourney API returned failed status: {}", response != null ? response.getStatus() : "");
            ekycAuditService.saveOrUpdate(journeyId, COMPLETE_JOURNEY, FAILED, "CompleteJourney API returned failed status");
            return;
        }

        ekycAuditService.saveOrUpdate(journeyId, COMPLETE_JOURNEY, SUCCESS, "");
    }

    private void createEkycRequest(EkycRequest request) {
        FileUtil.delete(EkycFilePath);
        File file = FileUtil.create(EkycFilePath);

        try {
            mapper.writeValue(file, request);
        } catch (IOException e) {
            log.error("Failed to write ekyc request to file {}", EkycFilePath, e);
            throw new RuntimeException();
        }
    }

    private EkycRequest getEkycRequest() {
        if (!FileUtil.exists(EkycFilePath)) {
            log.error("Ekyc file not found: {}", EkycFilePath);
            throw new RuntimeException();
        }

        try {
            File file = new File(EkycFilePath);
            return mapper.readValue(file, EkycRequest.class);
        } catch (IOException e) {
            log.error("Failed to get ekyc request: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    private void loadSampleImage(EkycRequest req) {
        if (req.getFrontId().isEmpty()) {
            req.setFrontId(ImageUtil.getFrontIdBase64());
        }

        if (req.getBackId().isEmpty()) {
            req.setBackId(ImageUtil.getBackIdBase64());
        }

        if (req.getSelfie().isEmpty()) {
            req.setSelfie(ImageUtil.getSelfieBase64());
        }

        if (req.getPassport().isEmpty()) {
            req.setPassport(ImageUtil.getPassportBase64());
        }
    }
}
