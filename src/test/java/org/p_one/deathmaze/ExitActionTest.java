package org.p_one.deathmaze;

import junit.framework.TestCase;

public class ExitActionTest extends TestCase {
	public void testExit() {
		Game game = new Game();
		Action action = new ExitAction();

		action.execute(game);

		assertEquals(Game.State.LOST, game.state);
	}
}
