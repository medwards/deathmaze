package org.p_one.deathmaze;

import junit.framework.TestCase;

public class NegotiateActionTest extends TestCase {
	public void testNegotiate() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END);
		game.map.add(room);
		game.addMonster(0, 0);
		Action action = new NegotiateAction();

		action.execute(game);

		assertEquals(Game.State.DEAD, game.state);
	}
}
