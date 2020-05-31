package com.dols.HistogramService.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Histogram {
	private List<HistogramData> data = Collections.synchronizedList(new ArrayList<>());
	
	public void append(List<HistogramData> wordScore) {
		data.addAll(wordScore);
	}
}
