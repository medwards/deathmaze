package org.p_one.deathmaze;

public class NegotiateAction implements Action {
	public void execute(Game game) {
		Room current = game.map.getRoom(game.player_x, game.player_y);
		if(game.getMonster(current.x, current.y) != null) {
			int dieResult = game.rollDice(2, 6) - 4;
			if(dieResult < 6) {
				game.state = Game.State.DEAD;
			}
		}
	}
}
