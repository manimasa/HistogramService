package com.dols.HistogramService.service;

import java.util.List;

import com.dols.HistogramService.model.Histogram;

public interface HistogramFactory {
	Histogram buildAndGetHistogramForUrlData(List<String>  urlData);
	Histogram buildHistogramMaxOf(int max, Histogram histogramData);

}
