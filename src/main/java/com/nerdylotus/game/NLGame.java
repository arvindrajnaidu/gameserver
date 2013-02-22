package com.nerdylotus.game;

import java.util.LinkedHashMap;


public abstract class NLGame {
	protected String scope = "global";
	protected String gameid = null;	
	protected String message = null;
	protected String winner = null;
	protected String owner = null;
	protected Integer maxPlayers = 2;
	protected Integer minPlayers = 2;
	protected Integer status = null;
	protected double nextGameIn = 3.0;
	protected Long startedAt = null;
	protected int eventNo = 0;
	protected Double buyin = 0.0;
	
	protected LinkedHashMap<String, NLPlayer> players = new LinkedHashMap<String, NLPlayer>();
	
	public LinkedHashMap<String, NLPlayer> getPlayers() {
		return players;
	}

	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public void setPlayers(LinkedHashMap<String, NLPlayer> players) {
		this.players = players;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getGameid() {
		return gameid;
	}


	public void setGameid(String gameid) {
		this.gameid = gameid;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Long getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Long startedAt) {
		this.startedAt = startedAt;
	}

	public Integer getMaxPlayers() {
		return maxPlayers;
	}


	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}


	public Integer getMinPlayers() {
		return minPlayers;
	}


	public void setMinPlayers(Integer minPlayers) {
		this.minPlayers = minPlayers;
	}


	public double getNextGameIn() {
		return nextGameIn;
	}


	public void setNextGameIn(double nextGameIn) {
		this.nextGameIn = nextGameIn;		
	}
	
	public void bumpEventNo(){
		this.eventNo = this.eventNo + 1;
	}
		
	public int getEventNo() {
		return eventNo;
	}

	public void setEventNo(int eventNo) {
		this.eventNo = eventNo;
	}
		
	public Double getBuyin() {
		return buyin;
	}

	public void setBuyin(Double buyin) {
		this.buyin = buyin;
	}

	public boolean areHumansPresent() {
		for(String tempPlayerId: players.keySet()){
			if(!players.get(tempPlayerId).isBot()){
				return true;
			}			
		}
		return false;
    }
	public abstract void reset();
	public abstract void clean();
	public abstract void setup();
	public abstract void teardown();
}
