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
        EkycAudit newAudit = EkycAudit.builder()
                .journeyId(journeyId)
                .type(type)
                .status(status)
                .remarks(remarks)
                .createdAt(LocalDateTime.now())
                .build();

        ekycAuditRepo.save(newAudit);
    }

    public List<EkycAudit> getAll(String journeyId) {
        return ekycAuditRepo.findAllByJourneyId(journeyId);
    }

}
