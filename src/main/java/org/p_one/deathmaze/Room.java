package org.p_one.deathmaze;

import java.util.Random;

public class Room {
	public boolean north, east, south, west;
	public int x, y;

	public Room(int x, int y) {
		Random generator = new Random();
		this.setup(x, y, generator.nextBoolean(), generator.nextBoolean(), generator.nextBoolean(), generator.nextBoolean());
	}

	public Room(int x, int y, boolean north, boolean east, boolean south, boolean west) {
		this.setup(x, y, north, east, south, west);
	}

	private void setup(int x, int y, boolean north, boolean east, boolean south, boolean west) {
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
		if(Math.abs(xDiff) + Math.abs(yDiff) != 1) {
			return false;
		}

		boolean myExit = false, otherExit = false;

		if(yDiff == -1) {
			myExit = this.south;
			otherExit = otherRoom.north;
		} else if(yDiff == 1) {
			myExit = this.north;
			otherExit = otherRoom.south;
		} else if(xDiff == -1) {
			myExit = this.east;
			otherExit = otherRoom.west;
		} else if(xDiff == 1) {
			myExit = this.west;
			otherExit = otherRoom.east;
		}

		return myExit && otherExit;
	}

	public boolean legal(Room otherRoom) {
		// intended to relieve the overloading of connected, but the
		// name sucks. Used to detect broken connections (which would
		// otherwise return false in connected)
		int xDiff = this.x - otherRoom.x;
		int yDiff = this.y - otherRoom.y;
		if(Math.abs(xDiff) + Math.abs(yDiff) == 0) {
			return false;
		} else if(Math.abs(xDiff) + Math.abs(yDiff) > 1) {
			return true;
		}

		boolean myExit = false, otherExit = false;

		if(yDiff == -1) {
			myExit = this.south;
			otherExit = otherRoom.north;
		} else if(yDiff == 1) {
			myExit = this.north;
			otherExit = otherRoom.south;
		} else if(xDiff == -1) {
			myExit = this.east;
			otherExit = otherRoom.west;
		} else if(xDiff == 1) {
			myExit = this.west;
			otherExit = otherRoom.east;
		}

		return myExit == otherExit;
	}
}
