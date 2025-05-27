package com.wan.ekyc.web;

import com.wan.ekyc.common.EkycDocType;
import com.wan.ekyc.common.ImageUtil;
import com.wan.ekyc.dto.ekyc.InitEkycRequest;
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

    @PostMapping("/init")
    public ResponseEntity<?> initEkyc(@RequestBody InitEkycRequest req) {
        // for testing purpose, load local sample image if not provided
        loadSampleImage(req);

        if (req.getDocType().equals(EkycDocType.ID.name())) {
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

        if (req.getDocType().equals(EkycDocType.PASSPORT.name())) {
            if (req.getFrontId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("frontId is required");
            }

            if (req.getSelfie().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("selfie is required");
            }
        }

        var result = service.initEkyc(req);
        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(result);
    }

    private void loadSampleImage(InitEkycRequest req) {
        if (req.getDocType().equals(EkycDocType.ID.name())) {
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

        if (req.getDocType().equals(EkycDocType.PASSPORT.name())) {
            if (req.getFrontId().isEmpty()) {
                req.setFrontId(ImageUtil.getPassportBase64());
            }

            if (req.getSelfie().isEmpty()) {
                req.setSelfie(ImageUtil.getSelfieBase64());
            }
        }

    }

}
