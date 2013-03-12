package com.nerdylotus.core.redis;

import com.nerdylotus.core.NLPub;

import redis.clients.jedis.Jedis;

public class NLRedisPubImpl extends Jedis implements NLPub{
	
	public NLRedisPubImpl(String host) {
		super(host);
	}

	public void pub(String channel, String message) {		
		publish(channel, message);
	}

}
