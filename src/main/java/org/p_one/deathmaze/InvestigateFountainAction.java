package org.p_one.deathmaze;

public class InvestigateFountainAction implements Action {
	public boolean execute(Game game) {
		Room current = game.map.getRoom(game.player_x, game.player_y);
		Chit.Feature feature = current.getFeature();
		if(Chit.Feature.FOUNTAIN == feature) {
			current.useFeature();
			int dieResult = game.rollDice(1, 6);
			if(dieResult == 1) {
				game.state = Game.State.DEAD;
			}
		}
		return false;
	}
}
