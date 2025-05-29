package com.wan.ekyc.repository;

import com.wan.ekyc.model.EkycLandmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EkycLandmarkRepository extends JpaRepository<EkycLandmark, Integer> {
    List<EkycLandmark> findAllByType(String type);
}
