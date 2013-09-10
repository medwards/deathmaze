package org.p_one.deathmaze;

public class InvestigateStatueAction implements Action {
	private String description = "investigated a statue";
	public boolean execute(Game game) {
		if(Game.State.PLAYING == game.state) {
			Room current = game.map.getRoom(game.player.x, game.player.y);
			Chit.Feature feature = current.getFeature();
			if(Chit.Feature.STATUE == feature) {
				current.useFeature();
				int dieResult = game.rollDice(1, 6);
				if(dieResult == 1 || dieResult == 2) {
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
