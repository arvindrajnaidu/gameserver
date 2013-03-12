package com.nerdylotus.core;

public interface NLPersistance {
	public String getString(String key);
	public void setString(String key, String value);
	public void addValueToKey(String key, String value);
	public String getRandomString(String key);
	public boolean lock(String key);
	public void unlock(String key);
	public void delete(String key);
	public Double getBalanceForMemberInScope(String key, String member);
	public void setBalanceForMemberInScope(String key, String member, Double score);
	public Double incrementBalanceOfMemberInScopeBy(String scope, String member, Double increment);
}
