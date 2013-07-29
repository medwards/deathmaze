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

	private Game gameState;
	public Screen screen;
	public ScreenWriter writer;
	public int x, y;

	public TerminalClient() {
		this.gameState = new Game();
		Room aRoom = new Room(0, 0, Chit.FOUR_WAY);
		this.gameState.map.add(aRoom);

		this.screen = TerminalFacade.createScreen();
		this.writer = new ScreenWriter(screen);
		this.x = -3;
		this.y = -3;
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
		int x_offset = 0 - x;
		int y_offset = 0 - y;
		for(Room room : this.gameState.map.rooms) {
			Terminal.Color color = Terminal.Color.WHITE;
			if(room.x == this.gameState.player_x && room.y == this.gameState.player_y) {
				color = Terminal.Color.YELLOW;
			}
			this.drawRoom(room, x_offset, y_offset, color);
		}
		this.drawPlayer(this.gameState, x_offset, y_offset, Terminal.Color.YELLOW);
		if(this.gameState.roomToPlace != null) {
			this.drawHighlight(x_offset, y_offset);
			Terminal.Color color = this.gameState.map.validRoom(this.gameState.roomToPlace) ? Terminal.Color.GREEN : Terminal.Color.RED;
			this.drawRoom(this.gameState.roomToPlace, x_offset, y_offset, color);
		}

		this.screen.refresh();
	}

	public void handleInput(Key input) {
		char character = Character.toUpperCase(input.getCharacter());
		Key.Kind kind = input.getKind();
		if(kind == Key.Kind.ArrowDown) {
			if(this.gameState.roomToPlace == null) {
				this.gameState.moveSouth();
			}
			this.forceFieldToCursor();
		} else if(kind == Key.Kind.ArrowUp) {
			if(this.gameState.roomToPlace == null) {
				this.gameState.moveNorth();
			}
			this.forceFieldToCursor();
		} else if(kind == Key.Kind.ArrowLeft) {
			if(this.gameState.roomToPlace == null) {
				this.gameState.moveWest();
			}
			this.forceFieldToCursor();
		} else if(kind == Key.Kind.ArrowRight) {
			if(this.gameState.roomToPlace == null) {
				this.gameState.moveEast();
			}
			this.forceFieldToCursor();
		} else if(character == 'A') {
			this.x--;
		} else if(character == 'D') {
			this.x++;
		} else if(character == 'W') {
			this.y--;
		} else if(character == 'S') {
			this.y++;
		} else if(this.gameState.roomToPlace != null && character == 'Z') {
			this.gameState.roomToPlace.rotate();
		} else if(character == ' ') {
			if(this.gameState.roomToPlace != null) {
				if(this.gameState.map.validRoom(this.gameState.roomToPlace)) {
					this.gameState.map.add(this.gameState.roomToPlace);
					this.gameState.roomToPlace = null;
				} else {
					this.gameState.roomToPlace = new Room(this.gameState.roomToPlace.x, this.gameState.roomToPlace.y);
				}
			}
		}
	}

	private void forceFieldToCursor() {
		int cursor_x = 0, cursor_y = 0;
		if(this.gameState.roomToPlace != null) {
			cursor_x = this.gameState.roomToPlace.x;
			cursor_y = this.gameState.roomToPlace.y;
		} else {
			cursor_x = this.gameState.player_x;
			cursor_y = this.gameState.player_y;
		}

		int x_offset = 0 - this.x;
		int y_offset = 0 - this.y;

		TerminalSize size = this.screen.getTerminal().getTerminalSize();
		if(0 > (cursor_x - this.x) * 5) {
			this.x = cursor_x - 2;
		}

		if(0 > (cursor_y - this.y) * 5) {
			this.y = cursor_y - 2;
		}

		if(size.getColumns() <= (cursor_x - this.x) * 5) {
			this.x = cursor_x -2;
		}

		if(size.getRows() <= (cursor_y - this.y) * 5) {
			this.y = cursor_y - 2;
		}
	}

	public void drawHighlight(int x_offset, int y_offset) {
		this.writer.setBackgroundColor(Terminal.Color.MAGENTA);
		int x, y;
		x = (this.gameState.roomToPlace.x + x_offset) * 5;
		y = (this.gameState.roomToPlace.y + y_offset) * 5;
		this.writer.drawString(x, y, "     ");
		this.writer.drawString(x, y + 1, "     ");
		this.writer.drawString(x, y + 2, "     ");
		this.writer.drawString(x, y + 3, "     ");
		this.writer.drawString(x, y + 4, "     ");
		this.writer.setBackgroundColor(Terminal.Color.DEFAULT);
	}

	public void drawRoom(Room room, int x_offset, int y_offset, Terminal.Color color) {
		int x = (room.x + x_offset) * 5;
		int y = (room.y + y_offset) * 5;
		this.writer.setBackgroundColor(color);
		this.writer.drawString(x + 1, y + 1, "   ");
		this.writer.drawString(x + 1, y + 2, "   ");
		this.writer.drawString(x + 1, y + 3, "   ");
		if(room.getNorth() == Chit.Exit.DOOR) {
			this.writer.drawString(x + 2, y + 0, " ");
		} else if(room.getNorth() == Chit.Exit.CORRIDOR) {
			this.writer.drawString(x + 1, y + 0, "   ");
		}
		if(room.getEast() == Chit.Exit.DOOR) {
			this.writer.drawString(x + 4, y + 2, " ");
		} else if(room.getEast() == Chit.Exit.CORRIDOR) {
			this.writer.drawString(x + 4, y + 1, " ");
			this.writer.drawString(x + 4, y + 2, " ");
			this.writer.drawString(x + 4, y + 3, " ");
		}

		if(room.getSouth() == Chit.Exit.DOOR) {
			this.writer.drawString(x + 2, y + 4, " ");
		} else if(room.getSouth() == Chit.Exit.CORRIDOR) {
			this.writer.drawString(x + 1, y + 4, "   ");
		}

		if(room.getWest() == Chit.Exit.DOOR) {
			this.writer.drawString(x + 0, y + 2, " ");
		} else if(room.getWest() == Chit.Exit.CORRIDOR) {
			this.writer.drawString(x + 0, y + 1, " ");
			this.writer.drawString(x + 0, y + 2, " ");
			this.writer.drawString(x + 0, y + 3, " ");
		}
		this.writer.setBackgroundColor(Terminal.Color.DEFAULT);
	}

	public void drawPlayer(Game state, int x, int y, Terminal.Color color) {
		x = ((state.player_x + x) * 5) + 2;
		y = ((state.player_y + y) * 5) + 2;
		this.writer.setBackgroundColor(color);
		this.writer.drawString(x, y, "⍟");
		this.writer.setBackgroundColor(Terminal.Color.DEFAULT);
	}
}
