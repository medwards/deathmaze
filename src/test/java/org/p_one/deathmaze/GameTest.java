package org.p_one.deathmaze;

import junit.framework.TestCase;

import java.util.Map;
import java.util.AbstractMap;

public class GameTest extends TestCase {
	public void testStartingState() {
		Game game = new Game();

		assertEquals(Game.State.PLAYING, game.state);
		assertEquals(0, game.player.x);
		assertEquals(0, game.player.y);
		assertNotNull(game.map);
		assertNull(game.roomToPlace);
		assertNotNull(game.monsters);
		assertEquals(0, game.monsters.size());
	}

	public void testFixedSeed() {
		Game game = new Game(new Long(0));
	}

	public void testActionLeaveDungeon() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.FOUR_WAY, true));

		game.action();

		assertEquals(Game.State.LOST, game.state);
	}

	public void testActionInvestigateFountain() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_FOUNTAIN);
		game.map.add(room);

		game.action();

		assertEquals(Chit.Feature.NONE, room.getFeature());
		assertEquals(Game.State.DEAD, game.state);
	}

	public void testActionInvestigateStatue() {
		Game game = new Game(new Long(0));
		Room room = new Room(0, 0, Chit.DEAD_END_STATUE);
		game.map.add(room);

		game.action();

		assertEquals(Chit.Feature.NONE, room.getFeature());
		assertEquals(Game.State.DEAD, game.state);
	}

	public void testActionInvestigateTrapdoor() {
		Game game = new Game(new Long(2));
		Room room = new Room(0, 0, Chit.DEAD_END_TRAPDOOR);
		game.map.add(room);

		game.action();

		assertEquals(Chit.Feature.NONE, room.getFeature());
		assertEquals(Game.State.DEAD, game.state);
	}

	public void testActionNegotiate() {
		Game game = new Game(new Long(0));
		game.map.add(new Room(0, 0, Chit.DEAD_END));
		Map.Entry<Integer,Integer> monster_coord = new AbstractMap.SimpleEntry(0, 0);
		game.monsters.add(monster_coord);

		game.action();

		assertEquals(Game.State.DEAD, game.state);
	}

	public void testActionQuit() {
		Game game = new Game();
		game.state = Game.State.DEAD;

		game.action();

		assertEquals(Game.State.QUIT, game.state);
	}

	public void testMove() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.CORRIDOR_WITH_DOORS));
		game.map.add(new Room(-1, 0, Chit.FOUR_WAY));
		game.map.add(new Room(0, 1, Chit.CORRIDOR_TURN));

		game.moveSouth();
		assertEquals(0, game.player.x);
		assertEquals(1, game.player.y);

		game.moveNorth();
		assertEquals(0, game.player.x);
		assertEquals(0, game.player.y);

		game.moveWest();
		assertEquals(-1, game.player.x);
		assertEquals(0, game.player.y);

		game.moveEast();
		assertEquals(0, game.player.x);
		assertEquals(0, game.player.y);
	}

	public void testMoveBlocked() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.DEAD_END));

		game.moveEast();
		game.moveSouth();
		game.moveWest();

		assertEquals(0, game.player.x);
		assertEquals(0, game.player.y);
	}

	public void testMoveOffBoardSwitchesToPlacement() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.DEAD_END));

		// should cause Chit.FOUR_WAY
		Chit.seed = new Long(0);
		game.moveNorth();

		assertEquals(0, game.player.x);
		assertEquals(-1, game.player.y);
		assertNotNull(game.roomToPlace);
		assertEquals(0, game.roomToPlace.x);
		assertEquals(-1, game.roomToPlace.y);

		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getNorth());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getEast());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getSouth());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getWest());
	}

	public void testMoveOffBoardDoesNotGiveImpossibleRooms() {
		Game game = new Game();
		// Make the only legal tile north of 0 a DEAD_END or TWO_WAY
		game.map.add(new Room(0, 0, Chit.FOUR_WAY));
		game.map.add(new Room(1, 0, Chit.FOUR_WAY));
		game.map.add(new Room(-1, 0, Chit.FOUR_WAY));
		game.map.add(new Room(1, -1, Chit.TWO_WAY));
		game.map.add(new Room(-1, -1, Chit.TWO_WAY));

		// Should try FOUR_WAY and others first
		Chit.seed = new Long(0);
		game.moveNorth();

		assertEquals(Chit.Exit.NONE, game.roomToPlace.getNorth());
		assertEquals(Chit.Exit.NONE, game.roomToPlace.getEast());
		assertEquals(Chit.Exit.DOOR, game.roomToPlace.getSouth());
		assertEquals(Chit.Exit.NONE, game.roomToPlace.getWest());
	}

	public void testPlaceRoom() {
		Game game = new Game();
		game.map.add(new Room(0, 0, Chit.DEAD_END));

		// should cause Chit.FOUR_WAY
		Chit.seed = new Long(0);
		game.moveNorth();
		Room placeableRoom = game.roomToPlace;
		game.placeRoom();

		assertEquals(placeableRoom, game.map.rooms.get(1));
		assertNull(game.roomToPlace);
	}

	public void testMonsters() {
		Game game = new Game();

		Map.Entry<Integer, Integer> monster;
		monster = new AbstractMap.SimpleEntry<Integer, Integer>(0, 0);
		game.monsters.add(monster);

		assertEquals(monster, game.getMonster(0, 0));
	}


	public void testPlaceRoomProducesMonsters() {
		Game game = new Game(new Long(1));
		game.map.add(new Room(0, 0, Chit.DEAD_END));

		// should cause Chit.FOUR_WAY
		Chit.seed = new Long(0);
		game.moveNorth();
		Room placeableRoom = game.roomToPlace;
		game.placeRoom();

		assertEquals(1, game.monsters.size());
		Map.Entry<Integer,Integer> monster_coord = new AbstractMap.SimpleEntry(0,-1);
		assertEquals(monster_coord, game.monsters.get(0));
	}

	public void testPlacedRoomsProduceMonsters() {
		Game game = new Game(new Long(0));
		game.map.add(new Room(0, 0, Chit.DEAD_END));
		game.map.add(new Room(0, -1, Chit.FOUR_WAY));

		game.moveNorth();

		assertEquals(1, game.monsters.size());
		Map.Entry<Integer,Integer> monster_coord = new AbstractMap.SimpleEntry(0,-1);
		assertEquals(monster_coord, game.monsters.get(0));
	}

	public void testAddAndGetMonster() {
		Game game = new Game(new Long(0));
		game.map.add(new Room(0, 0, Chit.DEAD_END));
		game.addMonster(0, 0);

		assertNotNull(game.getMonster(0, 0));
	}

	public void testRollDice() {
		Game game = new Game(new Long(0));

		int result = game.rollDice(4, 3);

		assertEquals(8, result);
	}
}
