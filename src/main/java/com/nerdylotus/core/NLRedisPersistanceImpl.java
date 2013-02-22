package com.nerdylotus.core;

import redis.clients.jedis.Jedis;

public class NLRedisPersistanceImpl extends Jedis implements NLPersistance {
	
	private static NLRedisPersistanceImpl me = null;
	private NLRedisPersistanceImpl(String host) {
		super(host);
	}
	
	public synchronized static NLRedisPersistanceImpl getInstance(){
		if(me == null){
			me = new NLRedisPersistanceImpl("localhost");
		}
		return me;
	}
	public String getValueForKey(String key){
		return me.get(key);
	}
	public void setValueForKey(String key, String value){
		me.set(key, value);
	}
	// Returns if lock was obtained
	public boolean lock(String key){
		return me.setnx("LK" + key, "LOCK") == 1;
	}
	public void unlock(String key){
		me.del("LK" + key);
	}
	public Double getValueForMemberInSet(String key, String member){
		return me.zscore(key, member);
	}
	public void setValueForMemberInSet(String key, String member, Double score){
		if(member == null || member.equals("undefined")) return;
		me.zadd(key, score, member);
	}
	public Double incrementBy(String key, String member, Double increment){
		return me.zincrby(key, increment, member);
	}
	public void addValueToKey(String key, String value){
		me.sadd(key, value);
	}
	public String getRandomValueInKey(String key){
		return me.srandmember(key);
	}
}
