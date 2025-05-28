package com.wan.ekyc.service;

import com.wan.ekyc.model.EkycAudit;
import com.wan.ekyc.repository.EkycAuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EkycAuditService {
    private final EkycAuditRepository ekycAuditRepo;

    public EkycAuditService(EkycAuditRepository ekycAuditRepo) {
        this.ekycAuditRepo = ekycAuditRepo;
    }

    public void saveOrUpdate(String journeyId, String type, String status, String remarks) {
        Optional<EkycAudit> audit = ekycAuditRepo.findByJourneyId(journeyId);

        if (audit.isEmpty()) {
            EkycAudit newAudit = EkycAudit.builder()
                    .journeyId(journeyId)
                    .type(type)
                    .status(status)
                    .createdAt(LocalDateTime.now())
                    .build();

            ekycAuditRepo.save(newAudit);
            return;
        }

        EkycAudit existingAudit = audit.get();
        existingAudit.setStatus(status);
        existingAudit.setRemarks(remarks);

        ekycAuditRepo.save(existingAudit);
    }

    public List<EkycAudit> getAll(String journeyId) {
        return ekycAuditRepo.findAllByJourneyId(journeyId);
    }

}
