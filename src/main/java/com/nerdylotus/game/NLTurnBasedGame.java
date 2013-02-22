package com.nerdylotus.game;

public abstract class NLTurnBasedGame extends NLGame {
	protected String turn;
	protected Long turnChangedAt = null;
	protected double turnTimeout = 20.0;
	protected boolean timeouts = true;
	
	public String getTurn() {
		return turn;
	}

	public boolean isTimeouts() {
		return timeouts;
	}

	public void setTimeouts(boolean timeouts) {
		this.timeouts = timeouts;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public Long getTurnChangedAt() {
		return turnChangedAt;
	}

	public void setTurnChangedAt(Long turnChangedAt) {
		this.turnChangedAt = turnChangedAt;
	}
	
	public double getTurnTimeout() {
		return turnTimeout;
	}

	public void setTurnTimeout(double turnTimeout) {
		this.turnTimeout = turnTimeout;
	}
	
	protected NLPlayer getNextPlayerFrom(String playername){
		boolean foundPlayer = false;		
		for(String name: this.players.keySet()){
			if(name.equals(playername)){
				// found the dude get next player
				foundPlayer = true;
				continue;
			}
			if(foundPlayer){
				return this.players.get(name);
			}
		}
		if(foundPlayer){
			// Last dude on the list ... so move it to the first dude
			for(String name: this.players.keySet()){
				return this.players.get(name);
			}
		}
		return null;
	}
	
	public abstract void rotateTurn();
	public abstract void turnExpired();
	public abstract void killTurnWatch();
}
