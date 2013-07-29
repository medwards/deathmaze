package org.p_one.deathmaze;

import java.util.Random;

public class Room {
	public Exit north, east, south, west;
	public int x, y;

	public Room(int x, int y) {
		this(x, y, Exit.getRandom(), Exit.getRandom(), Exit.getRandom(), Exit.getRandom());
	}

	public Room(int x, int y, Exit north, Exit east, Exit south, Exit west) {
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

	public Exit exit(int x_delta, int y_delta) {
		if(y_delta == -1) {
			return this.north;
		} else if(y_delta == 1) {
			return this.south;
		} else if(x_delta == 1) {
			return this.east;
		} else if(x_delta == -1) {
			return this.west;
		}
		return null;
	}

	public enum Exit {
		NONE,
		DOOR,
		CORRIDOR;

		public static Long seed = null;

		public static Exit getRandom() {
			Random generator;
			if(Exit.seed != null) {
				generator = new Random(Exit.seed.longValue());
			} else {
				generator = new Random();
			}
			Exit.seed = new Long(generator.nextLong());
			return Exit.values()[generator.nextInt(Exit.values().length)];
		}
	}
}
