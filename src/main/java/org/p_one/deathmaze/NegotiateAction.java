package org.p_one.deathmaze;

public class NegotiateAction implements Action {
	private String description = "negotiated with a monster";

	public boolean execute(Game game) {
		if(Game.State.PLAYING == game.state) {
			Room current = game.map.getRoom(game.player.x, game.player.y);
			if(game.getMonster(current.x, current.y) != null) {
				int dieResult = game.rollDice(2, 6) - 4;
				if(dieResult < 6) {
					game.state = Game.State.DEAD;
				}
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return this.description;
	}
}
