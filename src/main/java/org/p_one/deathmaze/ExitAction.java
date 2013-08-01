package org.p_one.deathmaze;

public class ExitAction implements Action {
	public boolean execute(Game game) {
		if(Game.State.PLAYING == game.state) {
			game.state = Game.State.LOST;
		}
		return false;
	}
}
