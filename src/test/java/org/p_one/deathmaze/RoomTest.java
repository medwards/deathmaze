package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.Room;

public class RoomTest extends TestCase {
	public void testCreate() {
		Room room = new Room(0, 0, true, true, false, false);
		assertEquals(room.north, true);
		assertEquals(room.east, true);
		assertEquals(room.south, false);
		assertEquals(room.west, false);
	}

	public void testRotate() {
		Room room = new Room(0, 0, true, false, false, true);
		room.rotate();
		assertEquals(room.north, true);
		assertEquals(room.east, true);
		assertEquals(room.south, false);
		assertEquals(room.west, false);
	}
}
