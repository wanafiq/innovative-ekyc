package com.wan.ekyc.service;

import com.wan.ekyc.model.EkycLandmark;
import com.wan.ekyc.repository.EkycLandmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EkycLandmarkService {
    private final EkycLandmarkRepository landmarkRepo;

    public Map<String, String> getGeneralThresholds() {
        List<EkycLandmark> thresholds = landmarkRepo.findAllByType("GENERAL");
        if (thresholds.isEmpty()) {
            return null;
        }

        Map<String, String> map = new HashMap<>();

        for (EkycLandmark t : thresholds) {
            map.put(t.getCode(), t.getSValue());
        }

        return map;
    }

    public Map<String, BigDecimal> getLandmarkThresholds(String type) {
        List<EkycLandmark> thresholds = landmarkRepo.findAllByType(type);
        if (thresholds.isEmpty()) {
            return null;
        }

        Map<String, BigDecimal> map = new HashMap<>();

        for (EkycLandmark t : thresholds) {
            map.put(t.getCode(), t.getDValue());
        }

        return map;
    }
}
