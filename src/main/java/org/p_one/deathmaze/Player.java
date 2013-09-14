package org.p_one.deathmaze;

public class Player {
	public int x, y;
	public int health;
	public int gold;

	public Player() {
		this.x = 0;
		this.y = 0;
		this.health = 20;
		this.gold = 0;
	}

	public void move(Room target) {
		this.x = target.x;
		this.y = target.y;
	}
}
