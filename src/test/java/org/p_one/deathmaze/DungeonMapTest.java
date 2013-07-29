package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Chit;
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
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);
		map.add(room);
		assertEquals(room, map.rooms.toArray()[0]);
	}

	public void testAddRoomMustHaveUniqueCoords() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);
		Room room2 = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);
		map.add(room);
		map.add(room2);
		assertEquals(1, map.rooms.size());
	}

	public void testAddedRoomsMustBeAdjacent() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR);
		map.add(room);

		Room adjacent_room = new Room(0, 1, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR);
		Room not_adj_room = new Room(0, 6, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR);
		map.add(adjacent_room);
		map.add(not_adj_room);

		assertTrue(map.rooms.contains(adjacent_room));
		assertFalse(map.rooms.contains(not_adj_room));
	}

	public void testValidRoomInEmptyMap() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);
		assertTrue(map.validRoom(room));
	}

	public void testValidRoomMustHaveUniqueCoords() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);
		map.add(room);

		Room room2 = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);

		assertFalse(map.validRoom(room2));
	}

	public void testValidRoomMustConnect() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.DOOR, Chit.Exit.NONE);
		map.add(room);

		Room connects_room = new Room(0, 1, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE, Chit.Exit.NONE);
		Room breaks_connection_room = new Room(-1, 0, Chit.Exit.NONE, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.NONE);
		Room not_connected_room = new Room(1, 0, Chit.Exit.DOOR, Chit.Exit.NONE, Chit.Exit.DOOR, Chit.Exit.NONE);
		Room distant_room = new Room(5, 5, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR);

		assertTrue(map.validRoom(connects_room));
		assertFalse(map.validRoom(not_connected_room));
		assertFalse(map.validRoom(breaks_connection_room));
		assertFalse(map.validRoom(distant_room));
	}

	public void testGetRoom() {
		DungeonMap map = new DungeonMap();
		Room base_room = new Room(1, 0, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR);
		map.add(base_room);
		Room room = new Room(0, 0, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR, Chit.Exit.DOOR);
		map.add(room);

		assertEquals(map.getRoom(0, 0), room);
	}
}
