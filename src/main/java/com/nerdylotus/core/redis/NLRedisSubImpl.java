package com.nerdylotus.core.redis;

import com.nerdylotus.core.NLSub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class NLRedisSubImpl extends Jedis implements NLSub{
	
	public NLRedisSubImpl(String host) {
		super(host);
	}

	public void sub(String channel) {
		// TODO Auto-generated method stub
		subscribe(new JedisPubSub() {
			
			@Override
			public void onUnsubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSubscribe(String arg0, int arg1) {
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
				
			}
			
			@Override
			public void onMessage(String arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		}, channel);
	}
}
