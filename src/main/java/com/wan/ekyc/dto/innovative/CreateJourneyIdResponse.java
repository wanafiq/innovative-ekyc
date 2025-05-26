package com.wan.ekyc.dto.innovative;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateJourneyIdResponse extends BaseResponse {
    private String journeyId;
}
