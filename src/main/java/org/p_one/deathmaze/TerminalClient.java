package org.p_one.deathmaze;

import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class TerminalClient {
	public static void main(String[] args) {
		DungeonMap map = new DungeonMap();
		Room room = new Room(true, true, true, true);
		map.rooms.add(room);
		System.out.println(map);
		System.out.println(room);
	}
}
