package thwack.maze;


public class AsciiRectangularMazePrinter {
	
	public void print(RectangularMaze maze) {
		
		for (int i = 0; i < maze.rooms.length; i++) {
			// draw the north edge
			for (int j = 0; j < maze.rooms[i].length; j++) {
				System.out.print(maze.rooms[j][i].north == null ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < maze.rooms[i].length; j++) {
				System.out.print(maze.rooms[j][i].west == null ? "|   " : "    ");
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < maze.rooms[0].length; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}

}
