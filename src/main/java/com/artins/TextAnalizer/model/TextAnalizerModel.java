package com.artins.TextAnalizer.model;



public class TextAnalizerModel {
	private int score;
	private String playerName;
	
	public TextAnalizerModel(String playerName, int score){
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
