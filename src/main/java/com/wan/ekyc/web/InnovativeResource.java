package com.wan.ekyc.web;

import com.wan.ekyc.service.InnovativeService;
import com.wan.ekyc.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/okid/{journeyId}/mykad")
    public ResponseEntity<?> initOkIdMykad(@PathVariable String journeyId) {
        String frontId = ImageUtil.getFrontIdBase64();
        if (frontId == null) {
            return ResponseEntity.badRequest().body("Front ID is required");
        }

        String backId = ImageUtil.getFrontIdBase64();
        if (backId == null) {
            return ResponseEntity.badRequest().body("Back ID is required");
        }

        var result = service.initOkId(journeyId, frontId, backId, true);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/okid/{journeyId}/passport")
    public ResponseEntity<?> initOkIdPassport(@PathVariable String journeyId) {
        String passport = ImageUtil.getPassportBase64();
        if (passport == null) {
            return ResponseEntity.badRequest().body("Passport is required");
        }

        var result = service.initOkId(journeyId, passport, null, false);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/okdoc/{journeyId}/mykad")
    public ResponseEntity<?> initOkDocMyKad(@PathVariable String journeyId) {
        String frontId = ImageUtil.getFrontIdBase64();
        if (frontId == null) {
            return ResponseEntity.badRequest().body("Front ID is required");
        }

        var result = service.initOkDoc(journeyId, frontId, true);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/okdoc/{journeyId}/passport")
    public ResponseEntity<?> initOkDocPassport(@PathVariable String journeyId) {
        String passport = ImageUtil.getPassportBase64();
        if (passport == null) {
            return ResponseEntity.badRequest().body("Passport is required");
        }

        var result = service.initOkDoc(journeyId, passport, false);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }
}
