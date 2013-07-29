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
		game.map.add(new Room(0, 0, Chit.Exit.NONE, Chit.Exit.CORRIDOR, Chit.Exit.DOOR, Chit.Exit.NONE));
		game.map.add(new Room(0, 1, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE));
		game.map.add(new Room(1, 0, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.CORRIDOR));

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
		game.map.add(new Room(0, 0, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE));

		game.moveNorth();
		game.moveEast();
		game.moveSouth();
		game.moveWest();

		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);
	}

	public void testMoveOffBoardSwitchesToPlacement() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE));

		// should cause DOOR, DOOR, DOOR, NONE
		Chit.Exit.seed = new Long(0);
		game.moveNorth();

		assertEquals(0, game.player_x);
		assertEquals(-1, game.player_y);
		assertNotNull(game.roomToPlace);
		assertEquals(0, game.roomToPlace.x);
		assertEquals(-1, game.roomToPlace.y);

		assertEquals(Chit.Exit.DOOR, game.roomToPlace.north);
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.east);
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.south);
		assertEquals(Chit.Exit.NONE, game.roomToPlace.west);
	}

	public void testMoveOffBoardDoesNotGiveImpossibleRooms() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE));

		// a normal new Room with this seed chould cause NONE, CORR, NONE, CORR
		Chit.Exit.seed = new Long(20);
		game.moveNorth();

		assertEquals(Chit.Exit.NONE, game.roomToPlace.north);
		assertEquals(Chit.Exit.CORRIDOR, game.roomToPlace.east);
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.south);
		assertEquals(Chit.Exit.NONE, game.roomToPlace.west);
	}
}
