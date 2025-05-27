package com.wan.ekyc.dto.innovative;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteJourneyRequest {
    private String apiKey;
    private String journeyId;
}
