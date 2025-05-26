package com.wan.ekyc.dto.innovative;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OkFaceRequest {
    // required
    private String apiKey;
    private String journeyId;
    @ToString.Exclude
    private String imageIdCardBase64; // Front id card image in base64
    @ToString.Exclude
    private String imageBestBase64; // Selfie image in base64
    private boolean livenessDetection; // Set to True - return liveness score
}
