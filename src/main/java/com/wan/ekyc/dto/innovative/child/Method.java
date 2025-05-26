package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Method {
    @JsonProperty("method")
    private String method;

    @JsonProperty("label")
    private String label;

    @JsonProperty("componentList")
    private List<Component> componentList;
}
