package com.dols.HistogramService.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HistogramServiceRequest {
    @ApiModelProperty(example = "https://www.aftonbladet.se/")
	private String url;
}
