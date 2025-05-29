package com.wan.ekyc.service;

import com.wan.ekyc.model.EkycThreshold;
import com.wan.ekyc.repository.EkycThresholdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wan.ekyc.common.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EkycThresholdService {
    private final EkycThresholdRepository thresholdRepo;

    public Map<String, String> getGeneralThreshold() {
        log.debug("Getting general threshold");
        List<EkycThreshold> thresholds = thresholdRepo.findAllByType(THRESHOLD_GENERAL);
        log.debug("General threshold size: {}", thresholds.size());

        Map<String, String> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getSValue());
        }
        return map;
    }

    public Map<String, BigDecimal> getOkDocThreshold() {
        log.debug("Getting OkDoc threshold");
        List<EkycThreshold> thresholds = thresholdRepo.findAllByTypeIn(List.of(THRESHOLD_NON_PASSPORT, THRESHOLD_PASSPORT));
        log.debug("OkDoc threshold size: {}", thresholds.size());

        Map<String, BigDecimal> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }
        return map;
    }

    public Map<String, BigDecimal> getOkFaceThreshold() {
        log.debug("Getting OkFace threshold");
        List<EkycThreshold> thresholds = thresholdRepo.findAllByType(THRESHOLD_FACE_VERIFICATION);
        log.debug("OkFace threshold size: {}", thresholds.size());

        Map<String, BigDecimal> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }
        return map;
    }

    public Map<String, BigDecimal> getOkLiveThreshold() {
        log.debug("Getting OkLive threshold");
        List<EkycThreshold> thresholds = thresholdRepo.findAllByType(THRESHOLD_LIVENESS);
        log.debug("OkLive threshold size: {}", thresholds.size());

        Map<String, BigDecimal> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }
        return map;
    }
}
