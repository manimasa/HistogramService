package com.dols.HistogramService.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistogramServiceResponse {
	private String word;
	private int frequency;
}
