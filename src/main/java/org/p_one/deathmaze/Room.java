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
}
