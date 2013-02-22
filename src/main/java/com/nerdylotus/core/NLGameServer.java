package com.nerdylotus.core;

import java.util.ArrayList;
import java.util.List;

import com.nerdylotus.error.NLConcurrentModificationException;
import com.nerdylotus.error.NLException;
import com.nerdylotus.game.NLCategoryLoader;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public abstract class NLGameServer {
	protected String subStream;
	protected String pubStream;
	protected String gameType;	
	protected int maxAttempts;
	protected NLGameMessageProcesser msgProcesser;
	protected NLCategoryLoader catLoader;
	
	protected List<NLGameConfig> gameConfigs = new ArrayList<NLGameConfig>();
	Jedis jedisForSub;
	Jedis jedisForPub;
	Jedis jedisForStorage;
	
	
	public NLGameServer(String gameType) {
		this.gameType = gameType;	
	}
	
	
	public void setup() {
		
		jedisForStorage = new Jedis("localhost");
		jedisForPub = new Jedis("localhost");
		jedisForSub = new Jedis("localhost");
		
		if(catLoader != null) catLoader.loadCategories();
		
		JedisPubSub jpubsub = new JedisPubSub() {
			
			@Override
			public void onUnsubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println("The game server has subscribe to Redis. We are all set.");
			}
			
			@Override
			public void onPUnsubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPSubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPMessage(String arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				System.out.println("PMessage");
			}

			@Override
			public void onMessage(String stream, String message){
				processMessage(stream, message, 1);
			}
			
			public void processMessage(String stream, String message, int attemptNo){
//				System.out.println("Message arrived" + stream + ":" + message);
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
					locked = jedisForStorage.setnx("LK" + gameid, "LOCK");
					if(locked == 1){
						String gameJSON = jedisForStorage.get(gameid);									
						NLGameUpdate update = msgProcesser.performAction(gameid, gamescope, gameconf, gameJSON, username, action);
						if(update != null){							
							// Increase the event number
							update.getGame().bumpEventNo();
							String gamestr = msgProcesser.getSerializer().gameToString(update.getGame());
							jedisForStorage.set(gameid, gamestr);
							String updatestr = msgProcesser.getSerializer().updateToString(update);
							// Atomicity ends
							System.out.println(gameType + "-out : " + updatestr);
							jedisForPub.publish(gameType + "-out", updatestr);								
						}else{
//							System.out.println("Update is null!");
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
						jedisForStorage.del("LK" + gameid);
					}
				}				
			}
		};
		jedisForSub.subscribe(jpubsub, gameType + "-in");
		
	}

}
