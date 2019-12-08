package com.verify.HstService.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Hist {
	private List<HstData> data = Collections.synchronizedList(new ArrayList<>());
	
	public void append(List<HstData> wordScore) {
		data.addAll(wordScore);
	}
}
