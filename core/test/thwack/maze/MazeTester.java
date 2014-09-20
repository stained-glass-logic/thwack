package thwack.maze;

import org.junit.Test;

public class MazeTester {

	
	@Test
	public void test() {
		RectangularMaze maze = new RectangularMaze(30, 30);
		
		StackMazeGenerator generator = new StackMazeGenerator();
		generator.generate(maze);
		
		AsciiRectangularMazePrinter printer = new AsciiRectangularMazePrinter();
		printer.print(maze);
	}
}
