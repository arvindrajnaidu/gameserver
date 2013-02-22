package com.nerdylotus.game;

public abstract class NLPlayer {
	protected String username;
	protected String name;
	protected String userData = "";
	protected long joinedAt = 0;
	protected boolean bot = false;
	protected boolean guest = false;
	
	
	public NLPlayer() {
		super();
	}
	public NLPlayer(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getJoinedAt() {
		return joinedAt;
	}
	public void setJoinedAt(long joinedAt) {
		this.joinedAt = joinedAt;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	public boolean isBot() {
		return bot;
	}
	public void setBot(boolean bot) {
		this.bot = bot;
	}
	public boolean isGuest() {
		return guest;
	}
	public void setGuest(boolean guest) {
		this.guest = guest;
	}
	
}
