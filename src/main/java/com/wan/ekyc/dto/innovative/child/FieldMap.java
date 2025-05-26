package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FieldMap {
    @JsonProperty("wLCID")
    private int wLCID;

    @JsonProperty("FieldType")
    private int fieldType;

    @JsonProperty("wFieldType")
    private int wFieldType;

    @JsonProperty("Field_MRZ")
    private String fieldMRZ;

    @JsonProperty("Field_Visual")
    private String fieldVisual;

    @JsonProperty("Matrix")
    private int[] matrix;
}
