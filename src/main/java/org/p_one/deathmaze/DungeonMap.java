package org.p_one.deathmaze;

import java.util.ArrayList;
import org.p_one.deathmaze.Room;

public class DungeonMap {
	public ArrayList<Room> rooms;
	public DungeonMap() {
		this.rooms = new ArrayList();
	}

	public void add(Room newRoom) {
		if(this.validRoom(newRoom)) {
			this.rooms.add(newRoom);
		}
	}

	public boolean validRoom(Room newRoom) {
		boolean adjacentToSomething = false;
		if(this.rooms.size() == 0) { adjacentToSomething = true; }
		for(Room room : this.rooms) {
			if(room.x == newRoom.x && room.y == newRoom.y) {
				return false;
			}
			int xDiff = Math.abs(room.x - newRoom.x);
			int yDiff = Math.abs(room.y - newRoom.y);
			int totalDiff = xDiff + yDiff;
			if(totalDiff == 1) {
				adjacentToSomething = true;
			}
		}
		if(!adjacentToSomething) {
			return false;
		}
		return true;
	}
}
