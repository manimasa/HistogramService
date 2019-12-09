package com.doubleverify.HstService.model;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HstData {
	String word;
	int frequency;
	
	public boolean equals(Object o) {
	    if (this == o)
	        return true;
	    if (o == null)
	        return false;
	    if (getClass() != o.getClass())
	        return false;
	    HstData wordScore = (HstData) o;
	    return Objects.equals(word, wordScore.word);
	}
}
