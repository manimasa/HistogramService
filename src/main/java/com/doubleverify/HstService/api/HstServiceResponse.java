package com.doubleverify.HstService.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HstServiceResponse {
	private String word;
	private int frequency;
}
