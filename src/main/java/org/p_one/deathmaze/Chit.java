package org.p_one.deathmaze;

import java.util.Random;

public enum Chit {
	FOUR_WAY(Exit.DOOR, Exit.DOOR, Exit.DOOR, Exit.DOOR),
	THREE_WAY(Exit.DOOR, Exit.DOOR, Exit.DOOR, Exit.NONE),
	TWO_WAY_STRAIGHT(Exit.DOOR, Exit.NONE, Exit.DOOR, Exit.NONE),
	TWO_WAY_TURN(Exit.DOOR, Exit.DOOR, Exit.NONE, Exit.NONE),
	DEAD_END(Exit.DOOR, Exit.NONE, Exit.NONE, Exit.NONE);

	public final Exit[] exits;
	public static Long seed = null;

	Chit(Exit ... exits) {
		this.exits = new Exit[4];
		for(int i = 0; i < 4; i++) {
			this.exits[i] = exits[i];
		}
	}

	public static Chit getRandom() {
		Random generator;
		if(Chit.seed != null) {
			generator = new Random(Chit.seed.longValue());
		} else {
			generator = new Random();
		}
		Chit.seed = new Long(generator.nextLong());
		return Chit.values()[generator.nextInt(Chit.values().length)];
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
