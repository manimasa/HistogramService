package com.artins.ServiceName.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artins.ServiceName.entity.ServiceNameEntity;
import com.artins.ServiceName.model.ServiceNameModel;
import com.artins.ServiceName.repository.ServiceNameRepository;

@Service
public class ServiceNameImpl implements ServiceName {
	private static final Logger log = LoggerFactory.getLogger(ServiceName.class);

	@Autowired
	ServiceNameRepository scoreCardrepo;

	@Override
	public List<ServiceNameModel> getScoreCard(String playerName) {
		log.info("Getting scorecards for player with player name {}", playerName);
		return map(scoreCardrepo.findByPlayerNameOrderByScoreDesc(playerName));
	}

	@Override
	public ServiceNameModel saveScoreCard(ServiceNameModel scoreCard) {	
		log.info("Saving scorecard for player with player name {}", scoreCard.getPlayerName());
		return map(scoreCardrepo.save(map(scoreCard)));
	}

	private List<ServiceNameModel> map(List<ServiceNameEntity> scoreCardEntities) {
		List<ServiceNameModel> scoreCardList = new ArrayList<>();
		
		for (ServiceNameEntity scoreCard : scoreCardEntities) {
			scoreCardList.add(new ServiceNameModel(scoreCard.getPlayerName(), scoreCard.getScore()));
		}
		
		return scoreCardList;
	}
	
	private ServiceNameEntity map(ServiceNameModel scoreCard) {
		return new ServiceNameEntity(scoreCard.getScore(), scoreCard.getPlayerName());
	}
	
	private ServiceNameModel map(ServiceNameEntity scorecardEntity) {
		return new ServiceNameModel(scorecardEntity.getPlayerName(), scorecardEntity.getScore());
	}

}
