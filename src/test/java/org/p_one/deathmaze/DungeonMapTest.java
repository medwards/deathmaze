package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class DungeonMapTest extends TestCase {
	public void testCreation() {
		DungeonMap map = new DungeonMap();
		assertNotNull(map.rooms);
	}

	public void testAddRoom() {
		DungeonMap map = new DungeonMap();
		Room room = new Room(0, 0, true, false, false, false);
		map.rooms.add(room);
		assertEquals(room, map.rooms.toArray()[0]);
	}

}
