package org.p_one.deathmaze;

import junit.framework.TestCase;

public class NegotiateActionTest extends TestCase {
	public void testNegotiate() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END);
		game.map.add(room);
		game.addMonster(0, 0);
		Action action = new NegotiateAction();

		boolean result = action.execute(game);

		assertEquals(true, result);
		assertEquals(Game.State.DEAD, game.state);
	}

	public void testNegotiateIgnoredWhenNoMonster() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END);
		game.map.add(room);
		Action action = new NegotiateAction();

		boolean result = action.execute(game);

		assertEquals(false, result);
		assertEquals(Game.State.PLAYING, game.state);
	}
}
