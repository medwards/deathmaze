package org.p_one.deathmaze;

import java.util.ArrayList;
import org.p_one.deathmaze.Room;
import org.p_one.deathmaze.InvalidRoomConnection;

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
		boolean connectedToSomething = false;
		if(this.rooms.size() == 0) { connectedToSomething = true; }
		for(Room room : this.rooms) {
			if(room.x == newRoom.x && room.y == newRoom.y) {
				return false;
			}
			int xDiff = Math.abs(room.x - newRoom.x);
			int yDiff = Math.abs(room.y - newRoom.y);
			int totalDiff = xDiff + yDiff;
			try {
				if(totalDiff == 1 && newRoom.connected(room)) {
					connectedToSomething = true;
				}
			} catch(InvalidRoomConnection e) {
				return false;
			}
		}
		if(!connectedToSomething) {
			return false;
		}
		return true;
	}

	public Room getRoom(int x, int y) {
		for(Room room : this.rooms) {
			if(room.x == x && room.y == y) {
				return room;
			}
		}
		return null;
	}
}
