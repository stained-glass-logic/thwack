package thwack.maze;

import java.util.ArrayList;
import java.util.List;

public class RectangularMaze implements Maze {
	
	final GridRoom[][] rooms;
	
	public RectangularMaze(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.rooms = new GridRoom[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				rooms[x][y] = new GridRoom(x, y);
			}
		}
	}
	
	public GridRoom at(int x, int y) {
		return validCoords(x, y) ? rooms[x][y] : null;
	}
	
	public GridRoom startingRoom() {
		return at(0, 0);
	}
	
	public List<Room> neighbors(Room r) {
		List<Room> neighbors = new ArrayList<Room>();
		
		GridRoom room = (GridRoom)r;
		
		if (validCoords(room.x + 1, room.y)) {
			neighbors.add(rooms[room.x + 1][room.y]);
		}

		if (validCoords(room.x - 1, room.y)) {
			neighbors.add(rooms[room.x - 1][room.y]);
		}
		
		if (validCoords(room.x, room.y + 1)) {
			neighbors.add(rooms[room.x][room.y + 1]);
		}

		if (validCoords(room.x, room.y - 1)) {
			neighbors.add(rooms[room.x][room.y - 1]);
		}

		return neighbors;
	}
	
	private boolean validCoords(int x, int y) {
		return x >= 0 && x < rooms.length && y >= 0 && y < rooms[0].length;
	}
	
	public static class GridRoom implements Maze.Room {
		
		int x;
		int y;
		
		GridRoom north = null;
		GridRoom south = null;
		GridRoom east = null;
		GridRoom west = null;

		public GridRoom(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void digNorth(GridRoom room) {
			this.north = room;
			room.south = this;
		}
		
		public void digSouth(GridRoom room) {
			this.south = room;
			room.north = this;
		}
		
		public void digEast(GridRoom room) {
			this.east = room;
			room.west = this;
		}
		
		public void digWest(GridRoom room) {
			this.west = room;
			room.east = this;
		}
		
		public void connect(Room r) {
			GridRoom room = (GridRoom)r;
			if (this.x == room.x) {
				if (this.y - 1 == room.y) {
					digNorth(room);
				} else if (this.y + 1 == room.y) {
					digSouth(room);
				}
			} else if (this.y == room.y) {
				if (this.x - 1 == room.x) {
					digWest(room);
				} else if (this.x + 1 == room.x) {
					digEast(room);
				}
			}
		}
	}
	
}
