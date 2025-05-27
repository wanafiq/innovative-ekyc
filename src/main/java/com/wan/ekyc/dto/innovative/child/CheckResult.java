package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckResult {
    @JsonProperty("checkType")
    private String checkType;

    @JsonProperty("checkStatus")
    private String checkStatus;
}
