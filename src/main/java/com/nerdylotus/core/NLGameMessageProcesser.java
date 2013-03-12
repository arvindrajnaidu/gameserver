package com.nerdylotus.core;

import net.sf.json.JSONObject;

import com.nerdylotus.error.NLConcurrentModificationException;
import com.nerdylotus.error.NLException;
import com.nerdylotus.error.NLInvalidOpException;

public abstract class NLGameMessageProcesser {
	int maxAttempts = 0;
	protected NLGameSerializer serializer;
	String gameType;
	
	public NLGameMessageProcesser(String gameType){
		this.gameType = gameType;
	}
//	public NLGameSerializer getSerializer() {
//		return serializer;
//	}
	public void setSerializer(NLGameSerializer serializer) {
		this.serializer = serializer;
	}
	public void parseAction(){
	}
	public void processMessage(String stream, String message, int attemptNo){
//		System.out.println("Message arrived" + stream + ":" + message);
		long locked = 0;
		String gameid = null;
		String gameconf = null;
		try{
			JSONObject jobj = JSONObject.fromObject(message);
			gameid = String.valueOf(jobj.get("gameid"));
			String username = (String)jobj.get("username");
			String action = (String)jobj.get("action");
			if(jobj.get("gameconf") != null){
				gameconf = ((JSONObject)jobj.get("gameconf")).toString(); 
			}					
			String gamescope = (String)jobj.get("scope");										
			// The following section has to be atomic 
			if(NLFactory.getPersistance().lock("LK" + gameid)){						
				String gameJSON = NLFactory.getPersistance().getString(gameid);									
				NLGameUpdate update = performAction(gameid, gamescope, gameconf, gameJSON, username, action);
				if(update != null){							
					// Increase the event number
					update.getGame().bumpEventNo();
					String gamestr = serializer.gameToString(update.getGame());
					NLFactory.getPersistance().setString(gameid, gamestr);
					String updatestr = serializer.updateToString(update);
					// Atomicity ends
//					System.out.println(gameType + "-out : " + updatestr);
					NLFactory.getPub().pub(gameType + "-out", updatestr);
//					jedisForPub.publish();								
				}else{
//					System.out.println("Update is null!");
				}
			}else{
				throw(new NLConcurrentModificationException("Could not obtain lock!"));
			}
		}catch(NLException nlioe){
			nlioe.printStackTrace();
			if(attemptNo < maxAttempts){
				processMessage(stream, message, attemptNo++);
			}
			System.out.println("******Giving up on update******");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(locked == 1 && gameid != null){
				NLFactory.getPersistance().delete("LK" + gameid);
			}
		}				
		
	}
	public abstract NLGameUpdate performAction(String gameid, String gamescope, String gamedata, String gameJSON, String username, String action) throws NLInvalidOpException;
}
