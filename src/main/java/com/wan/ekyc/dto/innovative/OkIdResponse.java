package com.wan.ekyc.dto.innovative;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wan.ekyc.dto.innovative.child.ResultItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OkIdResponse extends BaseResponse {
    @JsonProperty("result")
    private List<ResultItem> result;

    @JsonProperty("documentType")
    private String documentType;
}
