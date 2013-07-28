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

	public void testMove() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Room.Exit.NONE, Room.Exit.CORRIDOR, Room.Exit.DOOR, Room.Exit.NONE));
		game.map.add(new Room(0, 1, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE));
		game.map.add(new Room(1, 0, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.CORRIDOR));

		game.moveSouth();
		assertEquals(0, game.player_x);
		assertEquals(1, game.player_y);

		game.moveNorth();
		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);

		game.moveEast();
		assertEquals(1, game.player_x);
		assertEquals(0, game.player_y);

		game.moveWest();
		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);
	}
}
