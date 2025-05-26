package com.wan.ekyc.dto.innovative;

import lombok.*;
import org.springframework.core.io.ByteArrayResource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OkLiveRequest {
    private String apiKey;
    private String journeyId;
    @ToString.Exclude
    private ByteArrayResource imageBest; // Selfie image in bytes
}
