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

	public void testMoveBlocked() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE));

		game.moveNorth();
		game.moveEast();
		game.moveSouth();
		game.moveWest();

		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);
	}

	public void testMoveOffBoardSwitchesToPlacement() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE));

		// should cause DOOR, DOOR, DOOR, NONE
		Room.Exit.seed = new Long(0);
		game.moveNorth();

		assertEquals(0, game.player_x);
		assertEquals(-1, game.player_y);
		assertNotNull(game.roomToPlace);
		assertEquals(0, game.roomToPlace.x);
		assertEquals(-1, game.roomToPlace.y);

		assertEquals(Room.Exit.DOOR, game.roomToPlace.north);
		assertEquals(Room.Exit.DOOR, game.roomToPlace.east);
		assertEquals(Room.Exit.DOOR, game.roomToPlace.south);
		assertEquals(Room.Exit.NONE, game.roomToPlace.west);
	}
}
