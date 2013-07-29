package org.p_one.deathmaze;

import junit.framework.TestCase;

public class ChitTest extends TestCase {
	public void testChitProperties() {
		assertEquals(Chit.THREE_WAY.exits.length, 4);
	}
}
