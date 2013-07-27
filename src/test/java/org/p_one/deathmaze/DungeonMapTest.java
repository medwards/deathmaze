package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class DungeonMapTest extends TestCase {
	public void testCreation() {
		DungeonMap map = new DungeonMap();
		assertNotNull(map.rooms);
	}

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
}
