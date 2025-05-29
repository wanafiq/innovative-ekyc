package com.wan.ekyc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.ekyc.common.EkycUtil;
import com.wan.ekyc.common.FileUtil;
import com.wan.ekyc.dto.ekyc.EkycRequest;
import com.wan.ekyc.dto.ekyc.OkIdFields;
import com.wan.ekyc.dto.innovative.CreateJourneyIdResponse;
import com.wan.ekyc.dto.innovative.OkDocResponse;
import com.wan.ekyc.dto.innovative.OkIdResponse;
import com.wan.ekyc.model.EkycAudit;
import com.wan.ekyc.model.User;
import com.wan.ekyc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wan.ekyc.common.Constant.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EkycService {
    private final UserRepository userRepo;
    private final InnovativeService innovativeService;
    private final EkycAuditService ekycAuditService;
    private final EkycDocumentService ekycDocumentService;
    private final EkycLandmarkService ekycLandmarkService;

    private final String EkycFilePath = "src/main/resources/static/ekyc.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public User initEkyc(Long userId, EkycRequest req) {
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
            processOkDoc(ekycRequest);
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

        if (category.equals(NON_PASSPORT)) {
            response = innovativeService.initOkId(journeyId, frontId, backId, true);
        } else if(category.equals(PASSPORT)) {
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
            log.error("Failed to get document type, category: {}, type: {}", category, response.getDocumentType());
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "OkayId API returned failed status");
            return false;
        }

        OkIdFields fields = EkycUtil.translateOkIdFields(response);
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
        if (fields.getAddress2() == null || fields.getAddress2().isEmpty()) {
            log.error("Failed to get address2 from OkayID API");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_ID, FAILED, "Failed to get address2");
            return false;
        }
        if (fields.getAddress3() == null || fields.getAddress3().isEmpty()) {
            log.error("Address3 is not available");
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

    private void processOkDoc(EkycRequest request) {
        String category = request.getCategory();
        String journeyId = request.getJourneyId();
        String frontId = request.getFrontId();
        String passport = request.getPassport();

        OkDocResponse response;

        ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, IN_PROGRESS, null);

        if (category.equals(NON_PASSPORT)) {
            response = innovativeService.initOkDoc(journeyId, frontId, true);
        } else if(category.equals(PASSPORT)) {
            response = innovativeService.initOkDoc(journeyId, passport, false);
        } else {
            log.error("Invalid document category: {}", category);
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "Invalid document category");
            return;
        }

        if (response == null || !response.getStatus().equals("success")) {
            log.error("OkayDoc API returned failed status: {}", response != null ? response.getStatus() : "");
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "OkayDoc API returned failed status");
            return;
        }

        evaluateOkDoc(journeyId, category, response);
    }

    private void evaluateOkDoc(String journeyId, String category, OkDocResponse response) {
        Map<String, String> gr = EkycUtil.getGeneralResult(response);
        Map<String, String> gt = ekycLandmarkService.getGeneralThresholds();
        List<String> fgl = new ArrayList<>();

        for (Map.Entry<String, String> g : gr.entrySet()) {
            String expected = gt.get(g.getKey());
            String actual = g.getValue();
            boolean matched = actual.equals(expected);
            log.debug("Landmark: {}, Expected: {}, Actual: {}", g.getKey(), expected, actual);
            if (!matched) {
                fgl.add(g.getKey());
            }
        }

        log.info("General landmark checks: {}/{} failed", fgl.size(), gr.size());
        if (!fgl.isEmpty()) {
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "Failed general landmark checks");
        }

        Map<String, BigDecimal> ls = EkycUtil.getLandmarkScore(response);
        Map<String, BigDecimal> lt = ekycLandmarkService.getLandmarkThresholds(category);
        List<String> fls = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> l : ls.entrySet()) {
            BigDecimal expected = ls.get(l.getKey());
            BigDecimal actual = lt.get(l.getKey());
            boolean lessThan = actual.compareTo(expected) < 0;
            log.debug("Landmark: {}, Expected Score: {}, Actual Score: {}", l.getKey(), expected, actual);
            if (!lessThan) {
                fls.add(l.getKey());
            }
        }

        log.info("Landmark score checks: {}/{} failed", fls.size(), ls.size());
        if (!fls.isEmpty()) {
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, FAILED, "Failed landmark score checks");
        }

        if (fgl.isEmpty() && fls.isEmpty()) {
            ekycAuditService.saveOrUpdate(journeyId, OKAY_DOC, SUCCESS, "");
        }
    }

    public void createEkycRequest(EkycRequest request) {
        FileUtil.delete(EkycFilePath);
        File file = FileUtil.create(EkycFilePath);;

        try {
            mapper.writeValue(file, request);
        } catch (IOException e) {
            log.error("Failed to write ekyc request to file {}", EkycFilePath, e);
            throw new RuntimeException();
        }
    }

    public EkycRequest getEkycRequest() {
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

}
