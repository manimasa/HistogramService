package com.verify.HstService.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HstServiceRequest {
    @ApiModelProperty(example = "https://www.aftonbladet.se/")
	private String url;
}
