package com.nerdylotus.core;

import redis.clients.jedis.Jedis;

public class NLSharedPubRedis extends Jedis {
	private static NLSharedPubRedis me = null;
	private NLSharedPubRedis(String host) {
		super(host);
	}
	public synchronized static NLSharedPubRedis getInstance(){
		if(me == null){
			me = new NLSharedPubRedis("localhost");
		}
		return me;
	}
}
