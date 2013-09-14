package org.p_one.deathmaze;

import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap;

public class Game {
	public Player player;
	public DungeonMap map;
	public ArrayList<Map.Entry<Integer, Integer>> monsters;
	public Room roomToPlace;
	public Action lastAction;
	public State state;
	private Random generator;

	public Game() {
		this(null);
	}

	public Game(Long seed) {
		this.player = new Player();

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
		Action[] actions = {new NegotiateAction(), new ExitAction(), new InvestigateFountainAction(), new InvestigateStatueAction(), new InvestigateTrapdoorAction()};

		for(Action action : actions) {
			if(action.execute(this)) {
				this.lastAction = action;
				return;
			}
		}

		if(Game.State.DEAD == this.state || Game.State.LOST == this.state || Game.State.WON == this.state) {
			this.state = Game.State.QUIT;
		}
	}

	public void moveNorth() {
		this.move(this.player.x, this.player.y - 1);
	}

	public void moveEast() {
		this.move(this.player.x + 1, this.player.y);
	}

	public void moveWest() {
		this.move(this.player.x - 1, this.player.y);
	}

	public void moveSouth() {
		this.move(this.player.x, this.player.y + 1);
	}

	public void placeRoom() {
		if(this.roomToPlace != null) {
			this.map.add(this.roomToPlace);
			if(3 <= this.rollDice(1, 6)) {
				this.addMonster(this.roomToPlace.x, this.roomToPlace.y);
			}
			this.player.move(this.roomToPlace);
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

	private void move(int targetX, int targetY) {
		int x_delta = targetX - this.player.x;
		int y_delta = targetY - this.player.y;
		Room current = this.map.getRoom(this.player.x, this.player.y);
		Room proposed = this.map.getRoom(targetX, targetY);

		if(proposed == null && current.exit(x_delta, y_delta) != Chit.Exit.NONE) {
			this.roomToPlace = this.map.makeNewRoom(targetX, targetY);
		} else if(current != null && proposed != null && current.connected(proposed)) {
			this.player.move(proposed);
			if(1 == this.rollDice(1, 6)) {
				this.addMonster(this.player.x, this.player.y);
			}
		}
	}

	public void addMonster(int x, int y) {
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
