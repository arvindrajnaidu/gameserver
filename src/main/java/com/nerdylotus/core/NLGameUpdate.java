package com.nerdylotus.core;

import com.nerdylotus.game.NLGame;

public class NLGameUpdate {
	String causeCode = "";
	NLGame game = null;
	
	
	public NLGameUpdate(String causeCode, NLGame game) {
		super();
		this.causeCode = causeCode;
		this.game = game;
	}
	public String getCauseCode() {
		return causeCode;
	}
	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}
	public NLGame getGame() {
		return game;
	}
	public void setGame(NLGame game) {
		this.game = game;
	}	
}
