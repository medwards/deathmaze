package org.p_one.deathmaze;

import junit.framework.TestCase;

public class ChitTest extends TestCase {
	public void testChitProperties() {
		assertEquals(Chit.THREE_WAY.exits.length, 4);
	}

	public void testChitAtRandom() {
		Chit.seed = new Long(0);
		Chit chit = Chit.getRandom();
		assertEquals(Chit.THREE_WAY, chit);
	}
}
