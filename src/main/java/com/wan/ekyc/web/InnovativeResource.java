package com.wan.ekyc.web;

import com.wan.ekyc.service.InnovativeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/innovative")
public class InnovativeResource {

    private final InnovativeService service;

    public InnovativeResource(InnovativeService service) {
        this.service = service;
    }

    @GetMapping("/journeyId")
    public ResponseEntity<?> createJourneyId() {
        var result = service.CreateJourneyId();
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }
}
