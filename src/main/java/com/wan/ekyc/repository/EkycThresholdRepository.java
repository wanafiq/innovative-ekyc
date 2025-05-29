package com.wan.ekyc.repository;

import com.wan.ekyc.model.EkycThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EkycThresholdRepository extends JpaRepository<EkycThreshold, Integer> {
    List<EkycThreshold> findAllByType(String type);
    List<EkycThreshold> findAllByTypeIn(List<String> types);
}
