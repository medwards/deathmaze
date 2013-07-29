package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.Room;

public class RoomTest extends TestCase {
	public void testConstruct() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.CORRIDOR, Room.Exit.NONE, Room.Exit.NONE);
		assertEquals(room.north, Room.Exit.DOOR);
		assertEquals(room.east, Room.Exit.CORRIDOR);
		assertEquals(room.south, Room.Exit.NONE);
		assertEquals(room.west, Room.Exit.NONE);
	}

	public void testConstructRandom() {
		Room.Exit.seed = new Long(1);
		Room room = new Room(0, 0);

		assertEquals(room.north, Room.Exit.DOOR);
		assertEquals(room.east, Room.Exit.NONE);
		assertEquals(room.south, Room.Exit.CORRIDOR);
		assertEquals(room.west, Room.Exit.DOOR);
	}

	public void testRotate() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.DOOR);
		room.rotate();
		assertEquals(room.north, Room.Exit.DOOR);
		assertEquals(room.east, Room.Exit.DOOR);
		assertEquals(room.south, Room.Exit.NONE);
		assertEquals(room.west, Room.Exit.NONE);
	}

	public void testConnected() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room north_room = new Room(0, -1, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.DOOR, Room.Exit.NONE);

		assertTrue(room.connected(north_room));
		assertTrue(north_room.connected(room));
	}

	public void testNotConnectedWhenNotLinked() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room south_room = new Room(0, 1, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.DOOR, Room.Exit.NONE);

		assertFalse(room.connected(south_room));
	}

	public void testNotConnectedWhenNotAdjacent() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room distant_room = new Room(1, 1, Room.Exit.DOOR, Room.Exit.DOOR, Room.Exit.DOOR, Room.Exit.DOOR);
		assertFalse(room.connected(distant_room));
	}


	public void testNotConnectedWhenBroken() {
		Room room = new Room(0, 0, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room north_room = new Room(0, -1, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.DOOR, Room.Exit.NONE);

		assertFalse(room.connected(north_room));
	}

	public void testLegalWhenConnected() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room north_room = new Room(0, -1, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.DOOR, Room.Exit.NONE);

		assertTrue(room.legal(north_room));
		assertTrue(north_room.legal(room));
	}

	public void testLegalWhenNotAdjacent() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room north_room = new Room(0, -5, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.DOOR, Room.Exit.NONE);

		assertTrue(room.legal(north_room));
	}

	public void testLegalWhenUnconnected() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room south_room = new Room(0, 1, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);

		assertTrue(room.legal(south_room));
	}

	public void testNotLegalWhenOneSideConnected() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);
		Room south_room = new Room(0, 1, Room.Exit.DOOR, Room.Exit.NONE, Room.Exit.NONE, Room.Exit.NONE);

		assertFalse(room.legal(south_room));
	}

	public void testExitConvertsCoordsIntoExit() {
		Room room = new Room(0, 0, Room.Exit.DOOR, Room.Exit.CORRIDOR, Room.Exit.NONE, Room.Exit.NONE);
		assertEquals(room.exit(0, -1), Room.Exit.DOOR);
		assertEquals(room.exit(1, 0), Room.Exit.CORRIDOR);
		assertEquals(room.exit(0, 1), Room.Exit.NONE);
		assertNull(room.exit(2, 0));
	}
}
