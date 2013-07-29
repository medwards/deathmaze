package org.p_one.deathmaze;

import java.util.Random;

public enum Chit {
	THREE_WAY;

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
