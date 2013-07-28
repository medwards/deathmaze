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
}
