package org.p_one.deathmaze;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {
	public void testStartsAtOrigin() {
		Player player = new Player();
		assertEquals(0, player.x);
		assertEquals(0, player.y);
	}

	public void testMove() {
		Player player = new Player();
		Room target = new Room(5, 2, Chit.THREE_WAY);

		player.move(target);

		assertEquals(5, player.x);
		assertEquals(2, player.y);
	}
}
