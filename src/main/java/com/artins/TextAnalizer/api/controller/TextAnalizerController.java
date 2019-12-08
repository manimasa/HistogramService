package com.artins.TextAnalizer.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.artins.TextAnalizer.api.TextAnalizerRequest;
import com.artins.TextAnalizer.api.TextAnalizerResponse;
import com.artins.TextAnalizer.service.TextAnalizer;



@RestController
public class TextAnalizerController implements TextAnalizerResourceApi {
	
	@Autowired
	TextAnalizer service;

	@Override
	public List<TextAnalizerResponse> scoreUrlTexts(@Valid TextAnalizerRequest req) {
		//convert model to rest model
		return service.getScore(req.getUrl());
		
		//TODO:handle other cases
	}
	
	

}
