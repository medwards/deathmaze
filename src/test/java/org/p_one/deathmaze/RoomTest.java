package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.Room;

public class RoomTest extends TestCase {
	public void testConstruct() {
		Room room = new Room(0, 0, Chit.CORRIDOR_WITH_DOOR);
		assertEquals(Chit.Exit.CORRIDOR, room.getNorth());
		assertEquals(Chit.Exit.DOOR, room.getEast());
		assertEquals(Chit.Exit.CORRIDOR, room.getSouth());
		assertEquals(Chit.Exit.NONE, room.getWest());
	}

	public void testConstructRandom() {
		Chit.seed = new Long(0);
		Room room = new Room(0, 0);

		assertEquals(Chit.Exit.DOOR, room.getNorth());
		assertEquals(Chit.Exit.DOOR, room.getEast());
		assertEquals(Chit.Exit.DOOR, room.getSouth());
		assertEquals(Chit.Exit.NONE, room.getWest());
	}

	public void testRotate() {
		Room room = new Room(0, 0, Chit.THREE_WAY);
		room.rotate();
		assertEquals(Chit.Exit.NONE, room.getNorth());
		assertEquals(Chit.Exit.DOOR, room.getEast());
		assertEquals(Chit.Exit.DOOR, room.getSouth());
		assertEquals(Chit.Exit.DOOR, room.getWest());
	}

	public void testConnected() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room north_room = new Room(0, -1, Chit.TWO_WAY);

		assertTrue(room.connected(north_room));
		assertTrue(north_room.connected(room));
	}

	public void testNotConnectedWhenNotLinked() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room south_room = new Room(0, 1, Chit.DEAD_END);
		south_room.rotate();

		assertFalse(room.connected(south_room));
	}

	public void testNotConnectedWhenNotAdjacent() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room distant_room = new Room(1, 1, Chit.DEAD_END);
		assertFalse(room.connected(distant_room));
	}

	public void testNotConnectedWhenBroken() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room north_room = new Room(0, 1, Chit.DEAD_END);

		assertFalse(room.connected(north_room));
	}

	public void testLegalWhenConnected() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room north_room = new Room(0, -1, Chit.FOUR_WAY);

		assertTrue(room.legal(north_room));
		assertTrue(north_room.legal(room));
	}

	public void testLegalWhenNotAdjacent() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room north_room = new Room(0, -5, Chit.DEAD_END);

		assertTrue(room.legal(north_room));
	}

	public void testLegalWhenUnconnected() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room south_room = new Room(0, 1, Chit.DEAD_END);
		south_room.rotate();

		assertTrue(room.legal(south_room));
	}

	public void testNotLegalWhenOneSideConnected() {
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room south_room = new Room(0, 1, Chit.DEAD_END);

		assertFalse(room.legal(south_room));
	}

	public void testExitConvertsCoordsIntoExit() {
		Room room = new Room(0, 0, Chit.CORRIDOR_WITH_DOOR);
		assertEquals(room.exit(0, -1), Chit.Exit.CORRIDOR);
		assertEquals(room.exit(1, 0), Chit.Exit.DOOR);
		assertEquals(room.exit(0, 1), Chit.Exit.CORRIDOR);
		assertEquals(room.exit(-1, 0), Chit.Exit.NONE);
		assertNull(room.exit(2, 0));
	}

	public void testGetXDirection() {
		Room room = new Room(0, 0, Chit.CORRIDOR_WITH_DOOR);
		assertEquals(room.getNorth(), Chit.Exit.CORRIDOR);
		assertEquals(room.getEast(), Chit.Exit.DOOR);
		assertEquals(room.getSouth(), Chit.Exit.CORRIDOR);
		assertEquals(room.getWest(), Chit.Exit.NONE);
	}
}
