package com.artins.TextAnalizer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class WordHistogram {
	private List<WordScore> histogram = Collections.synchronizedList(new ArrayList<>());
	
	public void append(List<WordScore> wordScore) {
		histogram.addAll(wordScore);
	}
}
