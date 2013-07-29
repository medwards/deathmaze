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

	public void testLeaveDungeon() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.FOUR_WAY, true));

		game.action();

		assertEquals(Game.State.QUIT, game.state);
	}

	public void testMove() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.CORRIDOR_WITH_DOORS));
		game.map.add(new Room(-1, 0, Chit.FOUR_WAY));
		game.map.add(new Room(0, 1, Chit.CORRIDOR_TURN));

		game.moveSouth();
		assertEquals(0, game.player_x);
		assertEquals(1, game.player_y);

		game.moveNorth();
		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);

		game.moveWest();
		assertEquals(-1, game.player_x);
		assertEquals(0, game.player_y);

		game.moveEast();
		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);
	}

	public void testMoveBlocked() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.DEAD_END));

		game.moveEast();
		game.moveSouth();
		game.moveWest();

		assertEquals(0, game.player_x);
		assertEquals(0, game.player_y);
	}

	public void testMoveOffBoardSwitchesToPlacement() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.DEAD_END));

		// should cause Chit.TWO_WAY_STRAIGHT
		Chit.seed = new Long(0);
		game.moveNorth();

		assertEquals(0, game.player_x);
		assertEquals(-1, game.player_y);
		assertNotNull(game.roomToPlace);
		assertEquals(0, game.roomToPlace.x);
		assertEquals(-1, game.roomToPlace.y);

		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getNorth());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getEast());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getSouth());
		assertEquals(Chit.Exit.NONE, game.roomToPlace.getWest());
	}

	public void testMoveOffBoardDoesNotGiveImpossibleRooms() {
		Game game = new Game();
		// Make the only legal tile north of 0 a DEAD_END or TWO_WAY
		game.map.add(new Room(0, 0, Chit.FOUR_WAY));
		game.map.add(new Room(1, 0, Chit.FOUR_WAY));
		game.map.add(new Room(-1, 0, Chit.FOUR_WAY));
		game.map.add(new Room(1, -1, Chit.TWO_WAY));
		game.map.add(new Room(-1, -1, Chit.TWO_WAY));

		// Should try FOUR_WAY and THREE_WAY first
		Chit.seed = new Long(2);
		game.moveNorth();

		assertEquals(Chit.Exit.NONE, game.roomToPlace.getNorth());
		assertEquals(Chit.Exit.NONE, game.roomToPlace.getEast());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getSouth());
		assertEquals(Chit.Exit.NONE, game.roomToPlace.getWest());
	}
}
