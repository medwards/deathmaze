package org.p_one.deathmaze;

import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap;
import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class Game {
	public int player_x, player_y;
	public DungeonMap map;
	public ArrayList<Map.Entry<Integer, Integer>> monsters;
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
		this.monsters = new ArrayList();
		this.generator = new Random();
		if(seed != null) {
			this.generator = new Random(seed.longValue());
		}
		this.state = State.PLAYING;
	}

	public void action() {
		if(Game.State.PLAYING == this.state) {
			Room current = this.map.getRoom(this.player_x, this.player_y);

			if(current.isEntrance()) {
				this.state = Game.State.LOST;
			} else if(this.getMonster(current.x, current.y) != null) {
				int dieResult = this.rollDice(2, 6) - 4;
				if(dieResult < 6) {
					this.state = Game.State.DEAD;
				}
			} else if(Chit.Feature.NONE != current.getFeature()) {
				Chit.Feature feature = current.getFeature();
				current.useFeature();

				if(Chit.Feature.FOUNTAIN == feature) {
					int dieResult = this.rollDice(1, 6);
					if(dieResult == 1) {
						this.state = Game.State.DEAD;
					}
				} else if(Chit.Feature.STATUE == feature) {
					int dieResult = this.rollDice(1, 6);
					if(dieResult == 1 || dieResult == 2) {
						this.state = Game.State.DEAD;
					}
				} else if(Chit.Feature.TRAPDOOR == feature) {
					int dieResult = this.rollDice(1, 6);
					if(dieResult == 4 || dieResult == 5) {
						this.state = Game.State.DEAD;
					}
				}
			}
		} else if(Game.State.DEAD == this.state || Game.State.LOST == this.state || Game.State.WON == this.state) {
			this.state = Game.State.QUIT;
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

	public void placeRoom() {
		if(this.roomToPlace != null) {
			this.map.add(this.roomToPlace);
			if(3 <= this.rollDice(1, 6)) {
				this.addMonster(this.roomToPlace.x, this.roomToPlace.y);
			}
			this.roomToPlace = null;
		}
	}

	public int rollDice(int number, int sides) {
		int total = 0;
		for(int i = number; i > 0; i--) {
			total += this.generator.nextInt(sides) + 1;
		}
		return total;
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
			if(1 == this.rollDice(1, 6)) {
				this.addMonster(this.player_x, this.player_y);
			}
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

	private void addMonster(int x, int y) {
		this.monsters.add(new AbstractMap.SimpleEntry(x, y));
	}

	public Map.Entry<Integer, Integer> getMonster(int x, int y) {
		for(Map.Entry<Integer, Integer> monster : this.monsters) {
			if(monster.getKey() == x && monster.getValue() == y) {
				return monster;
			}
		}
		return null;
	}

	public enum State {
		SETUP,
		PLAYING,
		DEAD,
		WON,
		LOST,
		QUIT;
	}
}
