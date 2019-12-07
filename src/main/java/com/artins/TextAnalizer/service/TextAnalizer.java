package com.artins.TextAnalizer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artins.TextAnalizer.api.TextAnalizerResponse;


@Service
public interface TextAnalizer {
	List<TextAnalizerResponse>  getScore(String url);
}
