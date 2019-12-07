package com.artins.TextAnalizer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artins.TextAnalizer.model.TextAnalizerModel;
import com.artins.TextAnalizer.service.TextAnalizer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
public class TextAnalizerController implements TextAnalizerResourceApi {
	
	@Autowired
	TextAnalizer service;

	public List<TextAnalizerResponse> getScores(@Valid @PathVariable String playerName) {
		return map(service.getScoreCard(playerName));
	}
	
	
	public TextAnalizerResponse save(@Valid @RequestBody TextAnalizerRequest request) {
		return map(service.saveScoreCard(map(request)));
	}
	
	public List<TextAnalizerResponse> map(List<TextAnalizerModel> scoreCardModel){
		List<TextAnalizerResponse> scoreCardList = new ArrayList<>();
		
		for (TextAnalizerModel scoreCard : scoreCardModel) {
			scoreCardList.add(new TextAnalizerResponse(scoreCard.getPlayerName(), scoreCard.getScore()));
		}
		
		return scoreCardList;
	}
	
	private TextAnalizerModel map(TextAnalizerRequest scoreCardRequest) {
		return new TextAnalizerModel(scoreCardRequest.getPlayerName(), scoreCardRequest.getPoint());
	}
	
	private TextAnalizerResponse map(TextAnalizerModel ScoreCardModel) {
		return new TextAnalizerResponse(ScoreCardModel.getPlayerName(), ScoreCardModel.getScore());
	}
}
