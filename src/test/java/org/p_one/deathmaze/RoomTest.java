package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.Room;

public class RoomTest extends TestCase {
	public void testCreate() {
		Room room = new Room(0, 0, true, true, false, false);
		assertEquals(room.north, Room.Exit.DOOR);
		assertEquals(room.east, Room.Exit.DOOR);
		assertEquals(room.south, Room.Exit.NONE);
		assertEquals(room.west, Room.Exit.NONE);
	}

	public void testRotate() {
		Room room = new Room(0, 0, true, false, false, true);
		room.rotate();
		assertEquals(room.north, Room.Exit.DOOR);
		assertEquals(room.east, Room.Exit.DOOR);
		assertEquals(room.south, Room.Exit.NONE);
		assertEquals(room.west, Room.Exit.NONE);
	}

	public void testConnected() {
		Room room = new Room(0, 0, true, false, false, false);
		Room north_room = new Room(0, -1, false, false, true, false);

		assertTrue(room.connected(north_room));
		assertTrue(north_room.connected(room));
	}

	public void testNotConnectedWhenNotLinked() {
		Room room = new Room(0, 0, true, false, false, false);
		Room south_room = new Room(0, 1, false, false, true, false);

		assertFalse(room.connected(south_room));
	}

	public void testNotConnectedWhenNotAdjacent() {
		Room room = new Room(0, 0, true, false, false, false);
		Room distant_room = new Room(1, 1, true, true, true, true);
		assertFalse(room.connected(distant_room));
	}


	public void testNotConnectedWhenBroken() {
		Room room = new Room(0, 0, false, false, false, false);
		Room north_room = new Room(0, -1, false, false, true, false);

		assertFalse(room.connected(north_room));
	}

	public void testLegalWhenConnected() {
		Room room = new Room(0, 0, true, false, false, false);
		Room north_room = new Room(0, -1, false, false, true, false);

		assertTrue(room.legal(north_room));
		assertTrue(north_room.legal(room));
	}

	public void testLegalWhenNotAdjacent() {
		Room room = new Room(0, 0, true, false, false, false);
		Room north_room = new Room(0, -5, false, false, true, false);

		assertTrue(room.legal(north_room));
	}

	public void testLegalWhenUnconnected() {
		Room room = new Room(0, 0, true, false, false, false);
		Room south_room = new Room(0, 1, false, false, false, false);

		assertTrue(room.legal(south_room));
	}

	public void testNotLegalWhenOneSideConnected() {
		Room room = new Room(0, 0, true, false, false, false);
		Room south_room = new Room(0, 1, true, false, false, false);

		assertFalse(room.legal(south_room));
	}
}
