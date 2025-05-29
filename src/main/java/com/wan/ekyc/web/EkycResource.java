package com.wan.ekyc.web;

import com.wan.ekyc.dto.ekyc.EkycRequest;
import com.wan.ekyc.service.EkycService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ekyc")
public class EkycResource {
    private final EkycService service;

    public EkycResource(EkycService service) {
        this.service = service;
    }

    @PostMapping("/init/{userId}")
    public ResponseEntity<?> initEkyc(@PathVariable Long userId, @RequestBody EkycRequest req) {
        var result = service.initEkyc(userId, req);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/process/{journeyId}")
    public ResponseEntity<?> processEkyc(@PathVariable String journeyId) {
        var result = service.processEkyc(journeyId);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/audit/{journeyId}")
    public ResponseEntity<?> getAudits(@PathVariable String journeyId) {
        var result = service.getAudits(journeyId);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

}
