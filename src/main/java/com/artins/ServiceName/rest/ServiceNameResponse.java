package com.artins.ServiceName.rest;

public class ServiceNameResponse {
	private int point;
	private String playerName;
	
	public ServiceNameResponse(String playerName, int point){
		this.point = point;
		this.playerName = playerName;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getPoint() {
		return point;
	}

}
