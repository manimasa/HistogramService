package com.artins.ServiceName.rest;

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

import com.artins.ServiceName.model.ServiceNameModel;
import com.artins.ServiceName.service.ServiceName;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
public class ServiceNameController implements ServiceNameResourceApi {
	
	@Autowired
	ServiceName service;

	public List<ServiceNameResponse> getScores(@Valid @PathVariable String playerName) {
		return map(service.getScoreCard(playerName));
	}
	
	
	public ServiceNameResponse save(@Valid @RequestBody ServiceNameRequest request) {
		return map(service.saveScoreCard(map(request)));
	}
	
	public List<ServiceNameResponse> map(List<ServiceNameModel> scoreCardModel){
		List<ServiceNameResponse> scoreCardList = new ArrayList<>();
		
		for (ServiceNameModel scoreCard : scoreCardModel) {
			scoreCardList.add(new ServiceNameResponse(scoreCard.getPlayerName(), scoreCard.getScore()));
		}
		
		return scoreCardList;
	}
	
	private ServiceNameModel map(ServiceNameRequest scoreCardRequest) {
		return new ServiceNameModel(scoreCardRequest.getPlayerName(), scoreCardRequest.getPoint());
	}
	
	private ServiceNameResponse map(ServiceNameModel ScoreCardModel) {
		return new ServiceNameResponse(ScoreCardModel.getPlayerName(), ScoreCardModel.getScore());
	}
}
