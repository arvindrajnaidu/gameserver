package com.nerdylotus.core;

public abstract class NLGameConfig {
	String lookup = "";
	double buyin = 0.0;
	int minplayers = 1;
	int maxplayers = 2;
	
//	public NLGameConfig(String lookup, double buyin, int minplayers, int maxplayers) {
//		super();
//		this.lookup = lookup;
//		this.buyin = buyin;
//		this.minplayers = minplayers;
//		this.maxplayers = maxplayers;
//	}
	
	public double getBuyin() {
		return buyin;
	}

	public void setBuyin(double buyin) {
		this.buyin = buyin;
	}

	public int getMinplayers() {
		return minplayers;
	}
	public void setMinplayers(int minplayers) {
		this.minplayers = minplayers;
	}
	public int getMaxplayers() {
		return maxplayers;
	}
	public void setMaxplayers(int maxplayers) {
		this.maxplayers = maxplayers;
	}
	public String getLookup() {
		return lookup;
	}
	public void setLookup(String lookup) {
		this.lookup = lookup;
	}
}
