package com.wan.ekyc.dto.innovative;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OkayDocRequest {
    // required
    private String apiKey;
    private String journeyId;
    private String version; // API Version
    private String type; // nonpassport
    private String docType; // mykad
    @ToString.Exclude
    private String idImageBase64Image; // Front id card image in base64
    @ToString.Exclude
    private String halfSizeImage; // Passport image in base64
    private String country; // OTHER

    // mykad optional
    private Boolean idBrightnessDetection; // Set True to detect the brightness of the id image
    private Boolean idBlurDetection; // Set True to detect the blurriness of id image
    private Boolean ghostPhotoColorDetection; // Set True to do ghost photo color detection
    private Boolean screenDetection; // Set True to do screen detection
    private Boolean hologram; // Set True to do hologram detection
    private Boolean colorMode; // Set False to disable color checking
    private Boolean icTypeCheck; // Set False to disable IC type checking
    private Boolean photoSubstitutionCheck; // Set False to disable photo substitution checking
    private Boolean microprintCheck; // Set False to disable microprint checking
    private Boolean fontCheck; // Set False to disable font checking
    private Boolean landmarkCheck; // Set False to disable landmark checking

    public static OkayDocRequest buildMyKadRequest(String apiKey, String journeyId, String frontImage) {
        return OkayDocRequest.builder()
                .apiKey(apiKey)
                .journeyId(journeyId)
                .idImageBase64Image(frontImage)
                .version("7")
                .type("nonpassport")
                .docType("mykad")
                .idBrightnessDetection(true)
                .idBlurDetection(true)
                .ghostPhotoColorDetection(true)
                .screenDetection(true)
                .hologram(true)
                .colorMode(true)
                .icTypeCheck(true)
                .photoSubstitutionCheck(true)
                .microprintCheck(true)
                .fontCheck(true)
                .landmarkCheck(true)
                .build();
    }

    public static OkayDocRequest buildPassportRequest(String apiKey, String journeyId, String frontImage) {
        return OkayDocRequest.builder()
                .apiKey(apiKey)
                .journeyId(journeyId)
                .halfSizeImage(frontImage)
                .version("3")
                .type("passport")
                .country("OTHER")
                .build();
    }
}
