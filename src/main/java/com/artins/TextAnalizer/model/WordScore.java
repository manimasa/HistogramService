package com.artins.TextAnalizer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordScore {
	String word;
	int frequency;
}
