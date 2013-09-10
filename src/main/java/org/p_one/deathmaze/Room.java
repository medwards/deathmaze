package org.p_one.deathmaze;

public class Room {
	public int x, y;
	private Chit chit;
	private int NORTH, EAST, SOUTH, WEST; // kind of violating naming here

	private boolean entrance;
	private boolean used = false;

	public Room(int x, int y) {
		this(x, y, Chit.getRandom());
	}

	public Room(int x, int y, Chit chit) {
		this(x, y, chit, false);
	}

	public Room(int x, int y, Chit chit, boolean entrance) {
		this.entrance = entrance;

		this.chit = chit;
		this.NORTH = 0;
		this.EAST = 1;
		this.SOUTH = 2;
		this.WEST = 3;

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

	public boolean isEntrance() {
		return this.entrance;
	}

	public Chit.Feature getFeature() {
		if(this.used) {
			return Chit.Feature.NONE;
		}
		return this.chit.feature;
	}

	public void useFeature() {
		this.used = true;
	}

	public void rotate() {
		int oldWEST = this.WEST;
		this.WEST = this.SOUTH;
		this.SOUTH = this.EAST;
		this.EAST = this.NORTH;
		this.NORTH = oldWEST;
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
			return this.chit.exits[NORTH];
		} else if(y_delta == 1) {
			return this.chit.exits[SOUTH];
		} else if(x_delta == 1) {
			return this.chit.exits[EAST];
		} else if(x_delta == -1) {
			return this.chit.exits[WEST];
		}
		return null;
	}
}
