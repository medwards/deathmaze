package org.p_one.deathmaze;

import junit.framework.TestCase;

import org.p_one.deathmaze.DungeonMap;

public class DungeonMapTest extends TestCase {
	public void testCreation()
	{
		DungeonMap map = new DungeonMap();
		assertNotNull(map);
	}
}
