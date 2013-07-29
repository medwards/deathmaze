package org.p_one.deathmaze;

import org.p_one.deathmaze.Chit;

public class Room {
	public Chit.Exit north, east, south, west;
	public int x, y;

	public Room(int x, int y) {
		this(x, y, Chit.Exit.getRandom(), Chit.Exit.getRandom(), Chit.Exit.getRandom(), Chit.Exit.getRandom());
	}

	public Room(int x, int y, Chit.Exit north, Chit.Exit east, Chit.Exit south, Chit.Exit west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;

		this.x = x;
		this.y = y;
	}

	public void rotate() {
		Chit.Exit old_west = this.west;
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

		Chit.Exit myExit = Chit.Exit.NONE, otherExit = Chit.Exit.NONE;

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

		return myExit != Chit.Exit.NONE && myExit == otherExit;
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

		Chit.Exit myExit = Chit.Exit.NONE, otherExit = Chit.Exit.NONE;

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

	public Chit.Exit exit(int x_delta, int y_delta) {
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
}
