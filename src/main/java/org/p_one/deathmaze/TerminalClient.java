package org.p_one.deathmaze;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.TerminalFacade;
import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class TerminalClient {
	public static void main(String[] args) {
		TerminalClient client = new TerminalClient();
		client.run();
	}

	public DungeonMap map;
	public Screen screen;
	public ScreenWriter writer;

	public TerminalClient() {
		this.map = new DungeonMap();
		Room aRoom = new Room(true, true, true, true);
		this.map.rooms.add(aRoom);

		this.screen = TerminalFacade.createScreen();
		this.writer = new ScreenWriter(screen);

	}

	public void run() {
		this.screen.startScreen();
		for(Room room : this.map.rooms) {
			this.drawRoom(room);
		}

		this.screen.refresh();
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
		}
		this.screen.stopScreen();
	}

	public void drawRoom(Room room) {
		this.writer.drawString(1, 1, "***");
		this.writer.drawString(1, 2, "***");
		this.writer.drawString(1, 3, "***");
		if(room.north) {
			this.writer.drawString(0, 2, "*");
		}
		if(room.east) {
			this.writer.drawString(2, 4, "*");
		}
		if(room.south) {
			this.writer.drawString(4, 2, "*");
		}
		if(room.west) {
			this.writer.drawString(2, 0, "*");
		}
	}
}
