package org.p_one.deathmaze;

import org.p_one.deathmaze.Chit;

public class Room {
	private Chit.Exit north, east, south, west;
	public int x, y;

	public Room(int x, int y) {
		this(x, y, Chit.getRandom());
	}

	public Room(int x, int y, Chit chit) {
		this.north = chit.exits[0];
		this.east = chit.exits[1];
		this.south = chit.exits[2];
		this.west = chit.exits[3];

		this.x = x;
		this.y = y;
	}

	public Chit.Exit getNorth() {
		return this.exit(0, -1);
	}

	public Chit.Exit getEast() {
		return this.exit(1, 0);
	}

	public Chit.Exit getSouth() {
		return this.exit(0, 1);
	}

	public Chit.Exit getWest() {
		return this.exit(-1, 0);
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
			myExit = this.getSouth();
			otherExit = otherRoom.getNorth();
		} else if(yDiff == 1) {
			myExit = this.getNorth();
			otherExit = otherRoom.getSouth();
		} else if(xDiff == -1) {
			myExit = this.getEast();
			otherExit = otherRoom.getWest();
		} else if(xDiff == 1) {
			myExit = this.getWest();
			otherExit = otherRoom.getEast();
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
			myExit = this.getSouth();
			otherExit = otherRoom.getNorth();
		} else if(yDiff == 1) {
			myExit = this.getNorth();
			otherExit = otherRoom.getSouth();
		} else if(xDiff == -1) {
			myExit = this.getEast();
			otherExit = otherRoom.getWest();
		} else if(xDiff == 1) {
			myExit = this.getWest();
			otherExit = otherRoom.getEast();
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
