package com.dols.HistogramService.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.dols.HistogramService.api.HistogramServiceRequest;
import com.dols.HistogramService.api.HistogramServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface HstServiceResourceApi {
	@PostMapping
	ResponseEntity<List<HistogramServiceResponse>> getHistogram(@Valid @RequestBody HistogramServiceRequest req);
}
