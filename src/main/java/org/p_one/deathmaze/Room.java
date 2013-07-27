package org.p_one.deathmaze;

public class Room {
	public boolean north, east, south, west;
	public int x, y;
	public Room(int x, int y, boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;

		this.x = x;
		this.y = y;
	}

	public void rotate() {
		boolean old_west = this.west;
		this.west = this.south;
		this.south = this.east;
		this.east = this.north;
		this.north = old_west;
	}

	public boolean connected(Room otherRoom) {
		int xDiff = this.x - otherRoom.x;
		int yDiff = this.y - otherRoom.y;

		if(yDiff == -1) {
			return this.south && otherRoom.north;
		} else if(yDiff == 1) {
			return this.north && otherRoom.south;
		} else if(xDiff == -1) {
			return this.east && otherRoom.west;
		} else if(xDiff == 1) {
			return this.west && otherRoom.east;
		}

		return false;
	}
}
