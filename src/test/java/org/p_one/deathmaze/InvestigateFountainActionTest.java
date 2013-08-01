package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.InvestigateFountainAction;

public class InvestigateFountainActionTest extends TestCase {
	public void testInvestigateFountain() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_FOUNTAIN);
		game.map.add(room);
		Action action = new InvestigateFountainAction();

		action.execute(game);

		assertEquals(Chit.Feature.NONE, room.getFeature());
		assertEquals(Game.State.DEAD, game.state);
	}
}
