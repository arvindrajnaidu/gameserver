package com.nerdylotus.core;


import net.sf.json.JSONObject;

import com.nerdylotus.error.NLConcurrentModificationException;
import com.nerdylotus.error.NLException;
import com.nerdylotus.game.NLGame;

public abstract class NLDelayedTask extends Thread {
	String gameid;
	String gameType;
	int delay;
	int maxAttempts;
	NLGameSerializer serializer;
	
	public NLDelayedTask(String gameid, String gameType, int delay, int maxAttempts, NLGameSerializer serializer) {
		this.gameid = gameid;
		this.delay = delay;
		this.serializer = serializer;
		this.gameType = gameType;
		this.maxAttempts = maxAttempts;
	}
	@Override
	public void run() {
		try{
			sleep(delay * 1000);
			attemptTask(0);	
		}catch(InterruptedException ie){
			System.out.println("Turn thread interupted");
			ie.printStackTrace();
		}
	}
	public void attemptTask(int attemptNo){		
		boolean locked = false;
		try{
			// The following section has to be atomic
			locked = NLRedisPersistanceImpl.getInstance().lock(gameid);
			if(locked){
				String gameJSON = NLRedisPersistanceImpl.getInstance().getValueForKey(gameid);
				NLGameUpdate update = delayedTask(serializer.stringToGame(gameJSON));						
				NLRedisPersistanceImpl.getInstance().setValueForKey(gameid, serializer.gameToString(update.getGame()));
				// Atomicity ends
				System.out.println(gameType + "-out : " + JSONObject.fromObject(update).toString());
				NLSharedPubRedis.getInstance().publish(gameType + "-out", JSONObject.fromObject(update).toString());						
			}else{
				throw(new NLConcurrentModificationException("Could not obtain lock!"));
			}
		}catch(NLException nlioe){
			nlioe.printStackTrace();
			if(attemptNo < maxAttempts){
				attemptTask(attemptNo++);
			}
			System.out.println("******Giving up on delayed task******");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(locked){
				NLRedisPersistanceImpl.getInstance().unlock(gameid);
			}
		}												
	}
	abstract public NLGameUpdate delayedTask(NLGame game) throws NLException;
}
