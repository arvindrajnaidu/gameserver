package com.nerdylotus.core;

import net.sf.json.JSONObject;

public class NLDelayedAction extends Thread {
	String gameid;
	String gameType;
	String action;
	double delay;
	
	public NLDelayedAction(String gameid, String gameType, String action, double delay) {
		this.gameid = gameid;
		this.delay = delay;
		this.gameType = gameType;
		this.action = action;
	}
	@Override
	public void run() {
		try{
			sleep((long) (delay * 1000));
			JSONObject o = new JSONObject();
			o.put("gameid", gameid);
			o.put("username", "SERVER");
			o.put("action", action);	
			NLFactory.getPub().pub(gameType + "-in", o.toString());
		}catch(InterruptedException ie){
			System.out.println("Turn thread interupted");
		}
	}
}
