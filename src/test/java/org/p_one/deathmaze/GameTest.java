package org.p_one.deathmaze;

import junit.framework.TestCase;

public class GameTest extends TestCase {
	public void testStartingState() {
		Game game = new Game();

		assertEquals(game.player_x, 0);
		assertEquals(game.player_y, 0);
		assertNotNull(game.map);
		assertNull(game.roomToPlace);
	}
}
