package com.wan.ekyc.repository;

import com.wan.ekyc.model.EkycDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EkycDocumentRepository extends JpaRepository<EkycDocument, Long> {
    List<EkycDocument> findAllByCategory(String category);
}
