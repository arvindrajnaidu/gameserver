package com.nerdylotus.core;

import com.nerdylotus.core.redis.NLRedisPersistanceImpl;
import com.nerdylotus.core.redis.NLRedisPubImpl;
import com.nerdylotus.core.redis.NLRedisSubImpl;

public class NLFactory {
	
	private NLFactory(){}	

	private static class PersistanceHolder {
        private static final NLRedisPersistanceImpl INSTANCE = new NLRedisPersistanceImpl("localhost");
    }	
	public static NLPersistance getPersistance(){
		return PersistanceHolder.INSTANCE;
	}

	private static class PubHolder {
        private static final NLRedisPubImpl INSTANCE = new NLRedisPubImpl("localhost");
    }	
	public static NLPub getPub(){
		return PubHolder.INSTANCE;
	}

	private static class SubHolder {
        private static final NLRedisSubImpl INSTANCE = new NLRedisSubImpl("localhost");
    }	
	public static NLSub getSub(){
		return SubHolder.INSTANCE;
	}

}
