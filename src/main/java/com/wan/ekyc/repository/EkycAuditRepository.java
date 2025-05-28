package com.wan.ekyc.repository;

import com.wan.ekyc.model.EkycAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EkycAuditRepository extends JpaRepository<EkycAudit, Long> {
    Optional<EkycAudit> findByJourneyId(String journeyId);
    List<EkycAudit> findAllByJourneyId(String journeyId);
}
