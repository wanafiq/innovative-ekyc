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
        List<EkycThreshold> thresholds = thresholdRepo.findAllByType(THRESHOLD_GENERAL);

        Map<String, String> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getSValue());
        }

        return map;
    }

    public Map<String, BigDecimal> getOkDocThreshold() {
        List<EkycThreshold> thresholds = thresholdRepo.findAllByTypeIn(List.of(THRESHOLD_NON_PASSPORT, THRESHOLD_PASSPORT));

        Map<String, BigDecimal> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }

        return map;
    }

    public Map<String, BigDecimal> getOkFaceThreshold() {
        List<EkycThreshold> thresholds = thresholdRepo.findAllByType(THRESHOLD_FACE_VERIFICATION);

        Map<String, BigDecimal> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }

        return map;
    }

    public Map<String, BigDecimal> getOkLiveThreshold() {
        List<EkycThreshold> thresholds = thresholdRepo.findAllByType(THRESHOLD_LIVENESS);

        Map<String, BigDecimal> map = new HashMap<>();
        for (EkycThreshold t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }

        return map;
    }
}
