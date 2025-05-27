package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScorecardResult {
    @JsonProperty("scorecardStatus")
    private String scorecardStatus;

    @JsonProperty("docType")
    private String docType;

    @JsonProperty("checkResultList")
    private List<CheckResult> checkResultList;
}
