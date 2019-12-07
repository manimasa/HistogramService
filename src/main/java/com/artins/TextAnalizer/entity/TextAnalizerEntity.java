// tag::sample[]
package com.artins.TextAnalizer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;

@Entity
public class TextAnalizerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OrderBy("score ASC")
    private int score;
    private String playerName;

    protected TextAnalizerEntity() {}

    public TextAnalizerEntity(int score, String playerName) {
        this.score = score;
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return String.format(
                "ScoreCard[id=%d, WordScore='%d', playerName='%s']",
                id, score, playerName);
    }

// end::sample[]

	public Long getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public String getPlayerName() {
		return playerName;
	}
}

