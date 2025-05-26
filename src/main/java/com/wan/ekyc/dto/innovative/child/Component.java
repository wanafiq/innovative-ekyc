package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Component {
    @JsonProperty("code")
    private String code;

    @JsonProperty("label")
    private String label;

    @JsonProperty("value")
    private String value;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("refImageUrl")
    private String refImageUrl;
}
