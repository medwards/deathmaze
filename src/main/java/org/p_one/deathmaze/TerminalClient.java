package org.p_one.deathmaze;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.TerminalFacade;
import org.p_one.deathmaze.DungeonMap;
import org.p_one.deathmaze.Room;

public class TerminalClient {
	public static void main(String[] args) {
		DungeonMap map = new DungeonMap();
		Room aRoom = new Room(true, true, true, true);
		map.rooms.add(aRoom);

		Screen screen = TerminalFacade.createScreen();
		ScreenWriter writer = new ScreenWriter(screen);

		screen.startScreen();
		for(Room room : map.rooms) {
			writer.drawString(1, 1, "***");
			writer.drawString(2, 1, "***");
			writer.drawString(3, 1, "***");
			if(room.north) {
				writer.drawString(0, 2, "*");
			}
			if(room.east) {
				writer.drawString(2, 4, "*");
			}
			if(room.south) {
				writer.drawString(4, 2, "*");
			}
			if(room.west) {
				writer.drawString(2, 0, "*");
			}
		}

		screen.refresh();
		screen.getTerminal().readInput();
		screen.stopScreen();
	}
}
