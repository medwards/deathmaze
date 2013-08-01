package org.p_one.deathmaze;

import junit.framework.TestCase;

public class InvestigateStatueActionTest extends TestCase {
	public void testInvestigateStatue() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_STATUE);
		game.map.add(room);
		Action action = new InvestigateStatueAction();

		boolean result = action.execute(game);

		assertEquals(true, result);
		assertEquals(Chit.Feature.NONE, room.getFeature());
		assertEquals(Game.State.DEAD, game.state);
	}

	public void testNoActionIfNotStatue() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_TRAPDOOR);
		game.map.add(room);
		Action action = new InvestigateStatueAction();

		boolean result = action.execute(game);

		assertEquals(false, result);
		assertEquals(Chit.Feature.TRAPDOOR, room.getFeature());
		assertEquals(Game.State.PLAYING, game.state);
	}
}
