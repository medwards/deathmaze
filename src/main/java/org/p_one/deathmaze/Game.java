package org.p_one.deathmaze;

import java.util.Random;
import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class Game {
	public int player_x, player_y;
	public DungeonMap map;
	public Room roomToPlace;
	public State state;
	private Random generator;

	public Game() {
		this(null);
	}

	public Game(Long seed) {
		this.player_x = 0;
		this.player_y = 0;

		this.map = new DungeonMap();
		this.roomToPlace = null;
		this.generator = new Random();
		if(seed != null) {
			this.generator = new Random(seed.longValue());
		}
		this.state = State.PLAYING;
	}

	public void action() {
		Room current = this.map.getRoom(this.player_x, this.player_y);
		if(current.isEntrance()) {
			this.state = Game.State.QUIT;
		} else if (Chit.Feature.NONE != current.getFeature()) {
			Chit.Feature feature = current.getFeature();
			current.useFeature();

			if(Chit.Feature.FOUNTAIN == feature) {
				int dieResult = generator.nextInt(5) + 1;
				if(dieResult == 1) {
					this.state = Game.State.QUIT;
				}
			} else if(Chit.Feature.STATUE == feature) {
				int dieResult = generator.nextInt(5) + 1;
				if(dieResult == 1 || dieResult == 2) {
					this.state = Game.State.QUIT;
				}
			} else if(Chit.Feature.TRAPDOOR == feature) {
				int dieResult = generator.nextInt(5) + 1;
				if(dieResult == 4 || dieResult == 5) {
					this.state = Game.State.QUIT;
				}
			}
		}
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

		if(proposed == null && current.exit(x_delta, y_delta) != Chit.Exit.NONE) {
			this.roomToPlace = this.makeNewRoom(this.player_x + x_delta, this.player_y + y_delta);
			this.player_x += x_delta;
			this.player_y += y_delta;
		} else if(current != null && proposed != null && current.connected(proposed)) {
			this.player_x += x_delta;
			this.player_y += y_delta;
		}
	}

	private Room makeNewRoom(int x, int y) {
		Room room = new Room(x, y);
		while(!this.map.validRoom(room)) {
			for(int i = 0; i < 4; i++) {
				if(this.map.validRoom(room)) {
					return room;
				}
				room.rotate();
			}
			room = new Room(x, y);
		}

		return room;
	}

	public enum State {
		PLAYING,
		QUIT;
	}
}
