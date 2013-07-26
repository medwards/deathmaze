package org.p_one.deathmaze;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
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
	public int x, y;
	public int x_radius, y_radius;

	public TerminalClient() {
		this.map = new DungeonMap();
		Room aRoom = new Room(0, 0, true, true, true, true);
		this.map.rooms.add(aRoom);
		this.map.rooms.add(new Room(0, -1, false, false, true, false));
		this.map.rooms.add(new Room(1, 0, false, true, false, true));

		this.screen = TerminalFacade.createScreen();
		this.writer = new ScreenWriter(screen);
		this.x = 0;
		this.y = 0;
		this.x_radius = 3;
		this.y_radius = 1;
	}

	public void run() {
		this.screen.startScreen();
		Key key = null;
		while(key == null || key.getKind() != Key.Kind.Escape) {
			this.drawField(this.x, this.y);
			key = this.screen.getTerminal().readInput();
			if(key != null) {
				this.handleInput(key);
			}
		}
		this.screen.stopScreen();
	}

	public void drawField(int x, int y) {
		this.screen.clear();
		TerminalSize size = this.screen.getTerminal().getTerminalSize();
		int screen_x_slosh = size.getColumns() % 5;
		int screen_y_slosh = size.getRows() % 5;

		int x_extent = size.getColumns() / 5;
		if(x_extent % 2 == 0) {
			// this is the easiest way to accommodate the drawing
			// loop. Probably time to ditch the list impl even
			// if its for only an intermediate drawing 2d array
			x_extent--;
			screen_x_slosh += 5;
		}
		int y_extent = size.getRows() / 5;
		if(y_extent % 2 == 0) {
			y_extent--;
			screen_y_slosh += 5;
		}

		this.x_radius = x_extent / 2;
		this.y_radius = y_extent / 2;

	        int screen_x = this.x_radius * 5 + screen_x_slosh / 2;
		int screen_y = this.y_radius * 5 + screen_y_slosh / 2;





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
	}

	public void handleInput(Key input) {
		char character = Character.toUpperCase(input.getCharacter());
		Key.Kind kind = input.getKind();
		if(kind == Key.Kind.ArrowLeft || character == 'A') {
			this.x--;
		} else if(kind == Key.Kind.ArrowRight || character == 'D') {
			this.x++;
		} else if(kind == Key.Kind.ArrowUp || character == 'W') {
			this.y--;
		} else if(kind == Key.Kind.ArrowDown || character == 'S') {
			this.y++;
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
