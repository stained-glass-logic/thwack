package thwack.maze;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import thwack.maze.Maze.Room;

public class StackMazeGenerator {
	
	private Random random = new Random(System.currentTimeMillis());
	
	public void generate(Maze maze) {
		Set<Room> visited = new HashSet<Room>();
		
		Stack<Room> rooms = new Stack<Room>();
		
		Room current = maze.startingRoom();
		visited.add(current);
		
		do {
			
			List<Room> neighbors = maze.neighbors(current);
			neighbors.removeAll(visited);
			if (!neighbors.isEmpty()) {
				Room next = randomRoom(neighbors);
				rooms.push(current);
				
				current.connect(next);
				
				current = next;
				visited.add(current);
			} else if (!rooms.isEmpty()) {
				current = rooms.pop();
			}
		} while (!rooms.isEmpty());
		
	}
	
	private Room randomRoom(List<Room> rooms) {
		return rooms.get(random.nextInt(rooms.size()));
	}
}
