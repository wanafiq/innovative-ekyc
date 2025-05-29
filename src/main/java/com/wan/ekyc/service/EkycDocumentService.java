package com.wan.ekyc.service;

import com.wan.ekyc.model.EkycDocument;
import com.wan.ekyc.repository.EkycDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EkycDocumentService {
    private final EkycDocumentRepository documentRepo;

    public String getDocumentType(String category, String okIdType) {
        Map<String, String> map = new HashMap<>();

        List<EkycDocument> docs = documentRepo.findAllByCategory(category);
        for (var d : docs) {
            map.put(d.getName(), d.getType());
        }

        return map.get(okIdType);
    }
}
