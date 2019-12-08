package com.artins.TextAnalizer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class HistogramData {

	private List<WordScore> histogram = Collections.synchronizedList(new ArrayList<WordScore>());
	
	
	public void append(List<WordScore> scoreCard) {
		histogram.addAll(scoreCard);
	}
}
