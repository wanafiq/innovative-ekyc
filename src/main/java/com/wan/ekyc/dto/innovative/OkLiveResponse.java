package com.wan.ekyc.dto.innovative;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OkLiveResponse extends BaseResponse {
    @JsonProperty("probability")
    private BigDecimal probability;

    @JsonProperty("score")
    private BigDecimal score;

    @JsonProperty("quality")
    private BigDecimal quality;
}
