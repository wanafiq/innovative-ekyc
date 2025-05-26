package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultItem {
    @JsonProperty("ListVerifiedFields")
    private VerifiedFields verifiedFields;
}
