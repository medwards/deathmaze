package org.p_one.deathmaze;

import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class Game {
	public int player_x, player_y;
	public DungeonMap map;
	public Room roomToPlace;

	public Game() {
		this.player_x = 0;
		this.player_y = 0;

		this.map = new DungeonMap();
		this.roomToPlace = null;
	}

	public void moveNorth() {
		this.move(0, -1);
	}

	public void moveEast() {
		this.move(1,0);
	}

	public void moveWest() {
		this.move(-1, 0);
	}

	public void moveSouth() {
		this.move(0, 1);
	}


	private void move(int x_delta, int y_delta) {
		Room current = this.map.getRoom(this.player_x, this.player_y);
		Room proposed = this.map.getRoom(this.player_x + x_delta, this.player_y + y_delta);

		if(proposed == null && current.exit(x_delta, y_delta) != Room.Exit.NONE) {
			this.roomToPlace = new Room(this.player_x + x_delta, this.player_y + y_delta);
			this.player_x += x_delta;
			this.player_y += y_delta;
		} else if(current != null && proposed != null && current.connected(proposed)) {
				this.player_x += x_delta;
				this.player_y += y_delta;
		}
	}

}
