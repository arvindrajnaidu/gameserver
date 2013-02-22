package com.nerdylotus.core;

import com.nerdylotus.game.NLGame;

public abstract class NLGameSerializer {
	public abstract NLGame stringToGame(String strGame);
	public abstract String gameToString(NLGame game);
	public abstract String updateToString(NLGameUpdate update);
}
