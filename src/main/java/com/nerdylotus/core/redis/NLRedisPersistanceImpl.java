package com.nerdylotus.core.redis;

import com.nerdylotus.core.NLPersistance;

import redis.clients.jedis.Jedis;

public class NLRedisPersistanceImpl extends Jedis implements NLPersistance {
	
	public NLRedisPersistanceImpl(String host) {
		super(host);
	}
	
	public void setString(String key, String value){
		set(key, value);
	}
	public Double incrementBalanceOfMemberInScopeBy(String key, String member, Double increment){
		return zincrby(key, increment, member);
	}

	public String getRandomString(String key) {
		return srandmember(key);
	}

	public Double getBalanceForMemberInScope(String key, String member) {
		return zscore(key, member);
	}

	public void setBalanceForMemberInScope(String key, String member,
			Double score) {
		if(member == null || member.equals("undefined")) return;
		zadd(key, score, member);				
	}

	public String getString(String key) {
		return get(key);
	}

	public boolean lock(String key) {
		return setnx("LK" + key, "LOCK") == 1;
	}

	public void unlock(String key) {
		del("LK" + key);		
	}

	public void addValueToKey(String key, String value) {
		sadd(key, value);		
	}
	public void delete(String key) {
		del(key);		
	}
}
