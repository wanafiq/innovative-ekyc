package com.wan.ekyc.dto.innovative;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompleteJourneyResponse extends BaseResponse {
    @JsonProperty("completeDateTime")
    private long completeDateTime;
}
