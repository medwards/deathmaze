package org.p_one.deathmaze;

import java.util.Random;

public enum Chit {
	FOUR_WAY(Exit.DOOR, Exit.DOOR, Exit.DOOR, Exit.DOOR),
	THREE_WAY(Exit.DOOR, Exit.DOOR, Exit.DOOR, Exit.NONE),
	TWO_WAY(Exit.DOOR, Exit.NONE, Exit.DOOR, Exit.NONE),
	TWO_WAY_TURN(Exit.DOOR, Exit.DOOR, Exit.NONE, Exit.NONE),
	DEAD_END(Exit.DOOR, Exit.NONE, Exit.NONE, Exit.NONE),

	// Not in original: FOUR_WAY_CORRIDOR(Exit.CORRIDOR, Exit.CORRIDOR, Exit.CORRIDOR, Exit.CORRIDOR),
	// Not in original: THREE_WAY_CORRIDOR(Exit.CORRIDOR, Exit.CORRIDOR, Exit.CORRIDOR, Exit.NONE),
	CORRIDOR(Exit.CORRIDOR, Exit.NONE, Exit.CORRIDOR, Exit.NONE),
	CORRIDOR_TURN(Exit.CORRIDOR, Exit.CORRIDOR, Exit.NONE, Exit.NONE),
	// Not in original: DEAD_END_CORRIDOR(Exit.CORRIDOR, Exit.NONE, Exit.NONE, Exit.NONE),

	CORRIDOR_WITH_DOORS(Exit.CORRIDOR, Exit.DOOR, Exit.CORRIDOR, Exit.DOOR),
	CORRIDOR_WITH_DOOR(Exit.CORRIDOR, Exit.DOOR, Exit.CORRIDOR, Exit.NONE),
	CORRIDOR_TURN_WITH_DOORS(Exit.CORRIDOR, Exit.CORRIDOR, Exit.DOOR, Exit.DOOR),
	CORRIDOR_TURN_WITH_DOOR(Exit.CORRIDOR, Exit.CORRIDOR, Exit.NONE, Exit.DOOR),
	CORRIDOR_TURN_WITH_DOOR2(Exit.CORRIDOR, Exit.CORRIDOR, Exit.DOOR, Exit.NONE);

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
