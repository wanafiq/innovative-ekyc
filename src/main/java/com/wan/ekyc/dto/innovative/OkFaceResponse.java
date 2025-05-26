package com.wan.ekyc.dto.innovative;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wan.ekyc.dto.innovative.child.ImageBestLiveness;
import com.wan.ekyc.dto.innovative.child.ResultIdCard;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OkFaceResponse extends BaseResponse {
    @JsonProperty("imageBestLiveness")
    private ImageBestLiveness imageBestLiveness;

    @JsonProperty("result_idcard")
    private ResultIdCard resultIdCard;
}
