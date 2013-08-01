package org.p_one.deathmaze;

import junit.framework.TestCase;

public class ExitActionTest extends TestCase {
	public void testExit() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.DEAD_END, true));
		Action action = new ExitAction();

		boolean result = action.execute(game);

		assertEquals(true, result);
		assertEquals(Game.State.LOST, game.state);
	}

	public void testExitIgnoredWhenNotPlaying() {
		Game game = new Game();
		game.state = Game.State.SETUP;
		Action action = new ExitAction();

		boolean result = action.execute(game);

		assertEquals(false, result);
		assertEquals(Game.State.SETUP, game.state);

	}
}
