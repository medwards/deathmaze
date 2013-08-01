package org.p_one.deathmaze;

public class ExitAction implements Action {
	private String description = "left the dungeon";
	public boolean execute(Game game) {
		if(Game.State.PLAYING == game.state) {
			Room current = game.map.getRoom(game.player_x, game.player_y);
			if(current.isEntrance()) {
				game.state = Game.State.LOST;
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return this.description;
	}
}
