package com.artins.TextAnalizer.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextAnalizerResponse {
	private String word;
	private int frequency;
}
