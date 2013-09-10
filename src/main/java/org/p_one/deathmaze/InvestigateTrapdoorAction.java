package org.p_one.deathmaze;

public class InvestigateTrapdoorAction implements Action {
	private String description = "investigated a trap door";
	public boolean execute(Game game) {
		if(Game.State.PLAYING == game.state) {
			Room current = game.map.getRoom(game.player.x, game.player.y);
			Chit.Feature feature = current.getFeature();
			if(Chit.Feature.TRAPDOOR == feature) {
				current.useFeature();
				int dieResult = game.rollDice(1, 6);
				if(dieResult == 4 || dieResult == 5) {
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
