package com.artins.TextAnalizer.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artins.TextAnalizer.entity.TextAnalizerEntity;
import com.artins.TextAnalizer.model.TextAnalizerModel;
import com.artins.TextAnalizer.repository.TextAnalizerRepository;

@Service
public class TextAnalizerImpl implements TextAnalizer {
	private static final Logger log = LoggerFactory.getLogger(TextAnalizer.class);

	@Autowired
	TextAnalizerRepository scoreCardrepo;

	@Override
	public List<TextAnalizerModel> getScoreCard(String playerName) {
		log.info("Getting scorecards for player with player name {}", playerName);
		return map(scoreCardrepo.findByPlayerNameOrderByScoreDesc(playerName));
	}

	@Override
	public TextAnalizerModel saveScoreCard(TextAnalizerModel scoreCard) {	
		log.info("Saving scorecard for player with player name {}", scoreCard.getPlayerName());
		return map(scoreCardrepo.save(map(scoreCard)));
	}

	private List<TextAnalizerModel> map(List<TextAnalizerEntity> scoreCardEntities) {
		List<TextAnalizerModel> scoreCardList = new ArrayList<>();
		
		for (TextAnalizerEntity scoreCard : scoreCardEntities) {
			scoreCardList.add(new TextAnalizerModel(scoreCard.getPlayerName(), scoreCard.getScore()));
		}
		
		return scoreCardList;
	}
	
	private TextAnalizerEntity map(TextAnalizerModel scoreCard) {
		return new TextAnalizerEntity(scoreCard.getScore(), scoreCard.getPlayerName());
	}
	
	private TextAnalizerModel map(TextAnalizerEntity scorecardEntity) {
		return new TextAnalizerModel(scorecardEntity.getPlayerName(), scorecardEntity.getScore());
	}

}
