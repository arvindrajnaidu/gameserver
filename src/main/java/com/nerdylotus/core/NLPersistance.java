package com.nerdylotus.core;

public interface NLPersistance {
	public String getValueForKey(String key);
	public void setValueForKey(String key, String value);
	public void addValueToKey(String key, String value);
	public String getRandomValueInKey(String key);
	public boolean lock(String key);
	public void unlock(String key);
}
