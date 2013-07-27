package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class DungeonMapTest extends TestCase {
	public void testCreation() {
		DungeonMap map = new DungeonMap();
		assertNotNull(map.rooms);
	}

	// A lot of these are essentially DungeonMap.validRoom tests, need to
	// figure out mocking and just do this once
	public void testAddRoomToEmptyMap() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, false, false, false);
		map.add(room);
		assertEquals(room, map.rooms.toArray()[0]);
	}

	public void testAddRoomMustHaveUniqueCoords() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, false, false, false);
		Room room2 = new Room(0, 0, true, false, false, false);
		map.add(room);
		map.add(room2);
		assertEquals(1, map.rooms.size());
	}

	public void testAddedRoomsMustBeAdjacent() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, true, true, true);
		map.add(room);

		Room adjacent_room = new Room(0, 1, true, true, true, true);
		Room not_adj_room = new Room(0, 6, true, true, true, true);
		map.add(adjacent_room);
		map.add(not_adj_room);

		assertTrue(map.rooms.contains(adjacent_room));
		assertFalse(map.rooms.contains(not_adj_room));
	}

	public void testValidRoomInEmptyMap() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, false, false, false);
		assertTrue(map.validRoom(room));
	}

	public void testValidRoomMustHaveUniqueCoords() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, false, false, false);
		map.add(room);

		Room room2 = new Room(0, 0, true, false, false, false);

		assertFalse(map.validRoom(room2));
	}

	public void testValidRoomMustBeAdjacent() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, true, true, true);
		map.add(room);

		Room adjacent_room = new Room(0, 1, true, true, true, true);
		Room not_adj_room = new Room(0, 6, true, true, true, true);

		assertTrue(map.validRoom(adjacent_room));
		assertFalse(map.validRoom(not_adj_room));
	}

	public void testValidRoomMustConnect() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, false, true, false);
		map.add(room);

		Room connects_room = new Room(0, 1, true, false, false, false);
		Room breaks_connection_room = new Room(-1, 0, false, true, false, false);
		Room not_connected_room = new Room(1, 0, true, false, true, false);

		assertTrue(map.validRoom(connects_room));
		assertFalse(map.validRoom(not_connected_room));
		assertFalse(map.validRoom(breaks_connection_room));
	}
}
