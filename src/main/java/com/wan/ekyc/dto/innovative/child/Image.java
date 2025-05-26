package com.wan.ekyc.dto.innovative.child;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Image {
    @JsonProperty("tag")
    private String tag;

    @JsonProperty("imageUrl")
    private String imageUrl;
}
