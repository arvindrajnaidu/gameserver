package com.nerdylotus.core;

import com.nerdylotus.error.NLInvalidOpException;

public abstract class NLGameMessageProcesser {
	int maxAttempts = 0;
	protected NLGameSerializer serializer;
	
	public NLGameSerializer getSerializer() {
		return serializer;
	}
	public void setSerializer(NLGameSerializer serializer) {
		this.serializer = serializer;
	}
	public void parseAction(){
	}
	public abstract NLGameUpdate performAction(String gameid, String gamescope, String gamedata, String gameJSON, String username, String action) throws NLInvalidOpException;
}
