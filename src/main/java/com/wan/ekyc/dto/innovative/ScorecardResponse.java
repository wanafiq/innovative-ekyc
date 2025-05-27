package com.wan.ekyc.dto.innovative;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wan.ekyc.dto.innovative.child.ScorecardResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScorecardResponse extends BaseResponse {
    @JsonProperty("scorecardResultList")
    private List<ScorecardResult> scorecardResultList;
}
