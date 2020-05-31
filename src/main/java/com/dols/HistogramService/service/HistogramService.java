package com.dols.HistogramService.service;

import java.util.List;

import com.dols.HistogramService.api.HistogramServiceResponse;


public interface HistogramService {
	List<HistogramServiceResponse> getHistogram(String url);
}
