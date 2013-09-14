package org.p_one.deathmaze;

import junit.framework.TestCase;

public class DungeonMapTest extends TestCase {
	public void testCreation() {
		DungeonMap map = new DungeonMap();
		assertNotNull(map.rooms);
	}

	// A lot of these are essentially DungeonMap.validRoom tests, need to
	// figure out mocking and just do this once
	public void testAddRoomToEmptyMap() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.DEAD_END);
		map.add(room);
		assertEquals(room, map.rooms.toArray()[0]);
	}

	public void testAddRoomMustHaveUniqueCoords() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.DEAD_END);
		Room room2 = new Room(0, 0, Chit.DEAD_END);
		map.add(room);
		map.add(room2);
		assertEquals(1, map.rooms.size());
	}

	public void testAddedRoomsMustBeAdjacent() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.FOUR_WAY);
		map.add(room);

		Room adjacent_room = new Room(0, 1, Chit.FOUR_WAY);
		Room not_adj_room = new Room(0, 6, Chit.FOUR_WAY);
		map.add(adjacent_room);
		map.add(not_adj_room);

		assertTrue(map.rooms.contains(adjacent_room));
		assertFalse(map.rooms.contains(not_adj_room));
	}

	public void testValidRoomInEmptyMap() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.DEAD_END);
		assertTrue(map.validRoom(room));
	}

	public void testValidRoomMustHaveUniqueCoords() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.FOUR_WAY);
		map.add(room);

		Room room2 = new Room(0, 0, Chit.FOUR_WAY);

		assertFalse(map.validRoom(room2));
	}

	public void testValidRoomMustConnect() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, Chit.TWO_WAY);
		map.add(room);

		Room connects_room = new Room(0, 1, Chit.TWO_WAY);
		Room breaks_connection_room = new Room(-1, 0, Chit.TWO_WAY_TURN);
		Room not_connected_room = new Room(1, 0, Chit.TWO_WAY);
		Room distant_room = new Room(5, 5, Chit.FOUR_WAY);

		assertTrue(map.validRoom(connects_room));
		assertFalse(map.validRoom(not_connected_room));
		assertFalse(map.validRoom(breaks_connection_room));
		assertFalse(map.validRoom(distant_room));
	}

	public void testGetRoom() {
		DungeonMap map = new DungeonMap();
		Room base_room = new Room(1, 0, Chit.FOUR_WAY);
		map.add(base_room);
		Room room = new Room(0, 0, Chit.FOUR_WAY);
		map.add(room);

		assertEquals(map.getRoom(0, 0), room);
	}

	public void testMakesNewRandomRoom() {
		DungeonMap map = new DungeonMap();
		Chit.seed = new Long(0); // Should produce FOUR_WAY next
		Room newRoom = map.makeNewRoom(0, 0);

		assertEquals(Chit.Exit.DOOR, newRoom.getNorth());
		assertEquals(Chit.Exit.DOOR, newRoom.getEast());
		assertEquals(Chit.Exit.DOOR, newRoom.getSouth());
		assertEquals(Chit.Exit.DOOR, newRoom.getWest());
	}

	public void testMakeNewRoomGivesValidRoom() {
		DungeonMap map = new DungeonMap();
		// Make the only legal tile @ 0, -1 a DEAD_END or TWO_WAY
		map.add(new Room(0, 0, Chit.FOUR_WAY));
		map.add(new Room(1, 0, Chit.FOUR_WAY));
		map.add(new Room(-1, 0, Chit.FOUR_WAY));
		map.add(new Room(1, -1, Chit.TWO_WAY));
		map.add(new Room(-1, -1, Chit.TWO_WAY));

		// Should try FOUR_WAY and others first
		Chit.seed = new Long(0);
		Room newRoom = map.makeNewRoom(0, -1);

		assertEquals(Chit.Exit.NONE, newRoom.getNorth());
		assertEquals(Chit.Exit.NONE, newRoom.getEast());
		assertEquals(Chit.Exit.DOOR, newRoom.getSouth());
		assertEquals(Chit.Exit.NONE, newRoom.getWest());
	}
}
