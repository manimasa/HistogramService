package com.artins.ServiceName.model;



public class ServiceNameModel {
	private int score;
	private String playerName;
	
	public ServiceNameModel(String playerName, int score){
		this.score = score;
		this.playerName = playerName;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getScore() {
		return score;
	}
}
