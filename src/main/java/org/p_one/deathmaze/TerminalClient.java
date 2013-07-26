package org.p_one.deathmaze;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
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
	public int x_radius, y_radius;

	public TerminalClient() {
		this.map = new DungeonMap();
		Room aRoom = new Room(0, 0, true, true, true, true);
		this.map.rooms.add(aRoom);
		this.map.rooms.add(new Room(0, -1, false, false, true, false));
		this.map.rooms.add(new Room(1, 0, false, true, false, true));

		this.screen = TerminalFacade.createScreen();
		this.writer = new ScreenWriter(screen);
		this.x_radius = 3;
		this.y_radius = 1;
	}

	public void run() {
		int x = 0, y = 0;

		this.screen.startScreen();
		this.drawField(x, y);
		y--;
		this.drawField(x, y);
		y++;
		this.drawField(x, y);
		x++;
		this.drawField(x, y);

		this.screen.stopScreen();
	}

	public void drawField(int x, int y) {
		this.screen.clear();
	        int screen_x = this.x_radius * 5;
		int screen_y = this.y_radius * 5;
		for(Room room : this.map.rooms) {
			Terminal.Color color = Terminal.Color.WHITE;
			if(room.x == x && room.y == y) {
				color = Terminal.Color.YELLOW;
			}
			if(this.isRoomOnField(room, x, y)) {
				int room_screen_x = screen_x - ((x - room.x) * 5);
				int room_screen_y = screen_y - ((y - room.y) * 5);
				this.drawRoom(room, room_screen_x, room_screen_y, color);
			}
		}

		this.screen.refresh();
		try {
			Thread.sleep(1500);
		} catch(InterruptedException e) {
		}
	}

	public boolean isRoomOnField(Room room, int x, int y) {
		if(room.x >= x - this.x_radius && room.x <= x + x_radius && room.y >= y - y_radius && room.y <= y + y_radius) {
			return true;
		}
		return false;
	}

	public void drawRoom(Room room, int x, int y, Terminal.Color color) {
		this.writer.setBackgroundColor(color);
		this.writer.drawString(x + 1, y + 1, "   ");
		this.writer.drawString(x + 1, y + 2, "   ");
		this.writer.drawString(x + 1, y + 3, "   ");
		if(room.north) {
			this.writer.drawString(x + 2, y + 0, " ");
		}
		if(room.east) {
			this.writer.drawString(x + 4, y + 2, " ");
		}
		if(room.south) {
			this.writer.drawString(x + 2, y + 4, " ");
		}
		if(room.west) {
			this.writer.drawString(x + 0, y + 2, " ");
		}
		this.writer.setBackgroundColor(Terminal.Color.DEFAULT);
	}
}
