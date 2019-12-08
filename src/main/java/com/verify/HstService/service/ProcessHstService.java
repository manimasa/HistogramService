package com.verify.HstService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.verify.HstService.model.Hist;

@Service
public interface ProcessHstService {
	Hist getHistogram(List<String>  urlData);
	Hist getTopHistogramData(int max, Hist  histData);

}
