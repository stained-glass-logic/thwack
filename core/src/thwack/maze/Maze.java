package thwack.maze;

import java.util.List;

public interface Maze {

	Room startingRoom();

	List<Room> neighbors(Room current);

	public static interface Room {
		void connect(Room room);
	}
}
