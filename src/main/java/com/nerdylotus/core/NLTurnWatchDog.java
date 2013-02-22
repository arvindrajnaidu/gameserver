package com.nerdylotus.core;

import com.nerdylotus.error.NLException;
import com.nerdylotus.game.NLGame;
import com.nerdylotus.game.NLGameStatus;
import com.nerdylotus.game.NLTurnBasedGame;

public class NLTurnWatchDog extends NLDelayedTask {
		
	long turnChangedAt = 0;
	
	public NLTurnWatchDog(String gameid, String gameType, int delay,
			int maxAttempts, NLGameSerializer serializer, long turnChangedAt) {
		super(gameid, gameType, delay, maxAttempts, serializer);
		this.turnChangedAt = turnChangedAt;		
	}
	@Override
	public NLGameUpdate delayedTask(NLGame game) throws NLException {
		NLTurnBasedGame g = (NLTurnBasedGame) game;
		if(g.getStatus() == NLGameStatus.INPROGRESS &&
				g.getTurnChangedAt() == turnChangedAt){
			// Turn hasn't changed so far !! Fire the dude with the turn
			g.turnExpired();
		}
		return new NLGameUpdate("kicked", g);
	}
}
