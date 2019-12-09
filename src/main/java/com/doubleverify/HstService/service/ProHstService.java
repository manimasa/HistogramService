package com.doubleverify.HstService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doubleverify.HstService.model.Hist;

public interface ProHstService {
	Hist getHistogram(List<String>  urlData);
	Hist getTopHistogramData(int max, Hist  histData);

}
