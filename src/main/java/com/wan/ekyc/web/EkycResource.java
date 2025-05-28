package com.wan.ekyc.web;

import com.wan.ekyc.common.ImageUtil;
import com.wan.ekyc.dto.ekyc.EkycRequest;
import com.wan.ekyc.service.EkycService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wan.ekyc.common.Constant.NON_PASSPORT;
import static com.wan.ekyc.common.Constant.PASSPORT;

@RestController
@RequestMapping("/api/ekyc")
public class EkycResource {
    private final EkycService service;

    public EkycResource(EkycService service) {
        this.service = service;
    }

    @PostMapping("/init/{userId}")
    public ResponseEntity<?> initEkyc(@PathVariable Long userId, @RequestBody EkycRequest req) {
        // for testing purpose, load local sample image if not provided
        loadSampleImage(req);

        if (req.getCategory().equals(NON_PASSPORT)) {
            if (req.getFrontId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("frontId is required");
            }

            if (req.getBackId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("backId is required");
            }

            if (req.getSelfie().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("selfie is required");
            }
        }

        if (req.getCategory().equals(PASSPORT)) {
            if (req.getFrontId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("frontId is required");
            }

            if (req.getSelfie().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("selfie is required");
            }
        }

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

    private void loadSampleImage(EkycRequest req) {
        if (req.getCategory().equals(NON_PASSPORT)) {
            if (req.getFrontId().isEmpty()) {
                req.setFrontId(ImageUtil.getFrontIdBase64());
            }

            if (req.getBackId().isEmpty()) {
                req.setBackId(ImageUtil.getBackIdBase64());
            }

            if (req.getSelfie().isEmpty()) {
                req.setSelfie(ImageUtil.getSelfieBase64());
            }
        }

        if (req.getCategory().equals(PASSPORT)) {
            if (req.getFrontId().isEmpty()) {
                req.setFrontId(ImageUtil.getPassportBase64());
            }

            if (req.getSelfie().isEmpty()) {
                req.setSelfie(ImageUtil.getSelfieBase64());
            }
        }

    }

}
