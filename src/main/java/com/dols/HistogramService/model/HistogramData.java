package com.dols.HistogramService.model;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistogramData {
	String word;
	int frequency;
	
	public boolean equals(Object o) {
	    if (this == o)
	        return true;
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    HistogramData wordScore = (HistogramData) o;
	    return Objects.equals(word, wordScore.word);
	}
}
