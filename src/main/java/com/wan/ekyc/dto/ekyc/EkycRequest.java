package com.wan.ekyc.dto.ekyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EkycRequest {
    private String journeyId;
    private String category;
    private String frontId;
    private String backId;
    private String passport;
    private String selfie;
}
