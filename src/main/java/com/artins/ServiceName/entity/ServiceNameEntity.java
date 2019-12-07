// tag::sample[]
package com.artins.ServiceName.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;

@Entity
public class ServiceNameEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OrderBy("score ASC")
    private int score;
    private String playerName;

    protected ServiceNameEntity() {}

    public ServiceNameEntity(int score, String playerName) {
        this.score = score;
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return String.format(
                "ScoreCard[id=%d, Score='%d', playerName='%s']",
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

