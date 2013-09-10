package org.p_one.deathmaze;

import junit.framework.TestCase;

public class InvestigateFountainActionTest extends TestCase {
	public void testInvestigateFountain() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_FOUNTAIN);
		game.map.add(room);
		Action action = new InvestigateFountainAction();

		boolean result = action.execute(game);

		assertEquals(true, result);
		assertEquals(Chit.Feature.NONE, room.getFeature());
		assertEquals(Game.State.DEAD, game.state);
	}

	public void testNoActionIfNotFountain() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_STATUE);
		game.map.add(room);
		Action action = new InvestigateFountainAction();

		boolean result = action.execute(game);

		assertEquals(false, result);
		assertEquals(Chit.Feature.STATUE, room.getFeature());
		assertEquals(Game.State.PLAYING, game.state);
	}
}
