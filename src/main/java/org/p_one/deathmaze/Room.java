package org.p_one.deathmaze;

import java.util.Random;

public class Room {
	public Exit north, east, south, west;
	public int x, y;

	public Room(int x, int y) {
		this.setup(x, y, Exit.getRandom(), Exit.getRandom(), Exit.getRandom(), Exit.getRandom());
	}

	public Room(int x, int y, Exit north, Exit east, Exit south, Exit west) {
		this.setup(x, y, north, east, south, west);
	}

	public Room(int x, int y, boolean n, boolean e, boolean s, boolean w) {
		Exit north, east, south, west;
		north = n ? Exit.DOOR : Exit.NONE;
		east = e ? Exit.DOOR : Exit.NONE;
		south = s ? Exit.DOOR : Exit.NONE;
		west = w ? Exit.DOOR : Exit.NONE;

		this.setup(x, y, north, east, south, west);
	}

	private void setup(int x, int y, Exit north, Exit east, Exit south, Exit west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;

		this.x = x;
		this.y = y;
	}

	public void rotate() {
		Exit old_west = this.west;
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

		Exit myExit = Exit.NONE, otherExit = Exit.NONE;

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

		return myExit != Exit.NONE && myExit == otherExit;
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

		Exit myExit = Exit.NONE, otherExit = Exit.NONE;

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

	public enum Exit {
		NONE,
		DOOR,
		CORRIDOR;

		public static Exit getRandom() {
			Random generator = new Random();
			return Exit.values()[generator.nextInt(Exit.values().length)];
		}
	}
}
