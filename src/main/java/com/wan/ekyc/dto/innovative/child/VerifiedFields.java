package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VerifiedFields {
    @JsonProperty("pFieldMaps")
    private List<FieldMap> fieldMaps;
}
