package com.nerdylotus.core;

import java.util.ArrayList;
import java.util.List;

import com.nerdylotus.game.NLCategoryLoader;

public abstract class NLGameServer {
	protected String subStream;
	protected String pubStream;
	protected String gameType;	
	protected int maxAttempts;
	protected NLGameMessageProcesser msgProcesser;
	protected NLCategoryLoader catLoader; 
	
	protected List<NLGameConfig> gameConfigs = new ArrayList<NLGameConfig>();
	NLSub sub;
	NLPub pub;
	NLPersistance persistance;
	
	
	public NLGameServer(String gameType) {
		this.gameType = gameType;	
	}
	public String getGameType() {
		return gameType;
	}
	public void setup() {
//		Load Categories ..
		if(catLoader != null) catLoader.loadCategories();
//		Subscribe to channel
		NLFactory.getSub().sub(gameType + "-in");
	}

}
