package com.artins.TextAnalizer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artins.TextAnalizer.model.TextAnalizerModel;

@Service
public interface TextAnalizer {
	List<TextAnalizerModel> getScoreCard(String player);
	TextAnalizerModel saveScoreCard(TextAnalizerModel scoreCard);
}
