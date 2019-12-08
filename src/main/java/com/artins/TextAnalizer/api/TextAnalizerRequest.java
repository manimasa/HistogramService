package com.artins.TextAnalizer.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TextAnalizerRequest {
    @ApiModelProperty(example = "https://www.aftonbladet.se/")
	private String url;
}
