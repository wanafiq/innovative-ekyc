package com.wan.ekyc.dto.innovative;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OkIdRequest {
    // required
    private String apiKey;
    private String journeyId;
    @ToString.Exclude
    private String base64ImageString; // Front id card/passport image in base64

    // optional
    @ToString.Exclude
    private String backImage; // Back id card image in base64
    private String imageFormat; // Set to JPG, JPEG, PNG, BMP, GIF, TIFF, TIF depending on the image format, or leave it blank if not sure
    private boolean cambodia; // Set to true if it is Cambodia ID Card
    private boolean docTypeEnabled; // Set to true - document type will be returned
    private boolean faceImageEnabled; // Set to true - cropped face image will be returned
    private boolean imageEnabled; // Set to true - cropped document image will be returned

    public static OkIdRequest buildMyKadRequest(String apiKey, String journeyId, String frontId, String backId) {
        return OkIdRequest.builder()
                .apiKey(apiKey)
                .journeyId(journeyId)
                .base64ImageString(frontId)
                .backImage(backId)
                .cambodia(false)
                .docTypeEnabled(true)
                .faceImageEnabled(false)
                .imageEnabled(false)
                .build();
    }

    public static OkIdRequest buildPassportRequest(String apiKey, String journeyId, String passport) {
        return OkIdRequest.builder()
                .apiKey(apiKey)
                .journeyId(journeyId)
                .base64ImageString(passport)
                .cambodia(false)
                .docTypeEnabled(true)
                .faceImageEnabled(false)
                .imageEnabled(false)
                .build();
    }
}
