package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ImageBestLiveness {
    @JsonProperty("probability")
    private BigDecimal probability;

    @JsonProperty("score")
    private BigDecimal score;

    @JsonProperty("quality")
    private BigDecimal quality;
}
