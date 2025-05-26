package com.wan.ekyc.dto.innovative;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wan.ekyc.dto.innovative.child.Image;
import com.wan.ekyc.dto.innovative.child.Method;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OkDocResponse extends BaseResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("methodList")
    private List<Method> methodList;

    @JsonProperty("outputImageList")
    private List<Image> outputImageList;
}
