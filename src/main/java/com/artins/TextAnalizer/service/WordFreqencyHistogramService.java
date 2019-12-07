package com.artins.TextAnalizer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artins.TextAnalizer.model.WordHistogram;

@Service
public interface WordFreqencyHistogramService {
	WordHistogram getHistogram(List<String>  text);
}
