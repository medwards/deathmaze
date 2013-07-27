package org.p_one.deathmaze;

import java.util.ArrayList;
import org.p_one.deathmaze.Room;

public class DungeonMap {
	public ArrayList<Room> rooms;
	public DungeonMap() {
		this.rooms = new ArrayList();
	}

	public void add(Room newRoom) {
		for(Room room : this.rooms) {
			if(room.x == newRoom.x && room.y == newRoom.y) {
				return;
			}
		}
		this.rooms.add(newRoom);
	}
}
