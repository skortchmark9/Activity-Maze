package activity;
import activity.Door.Direction;


public class Maze {
	Room[][] rooms;
	int rows;
	int cols;
	
	
	Maze(String path) {
		
	}
	
	Maze(int startX, int startY, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		rooms = new Room[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				rooms[row][col] = new Room(row, col);
			}
		}
		
		drunkenWalk(startX, startY);
		resetVisited();
	}
	
	Maze(int rows, int cols) {
		this(0, 0, rows, cols);
	}
	
	public void resetVisited() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				rooms[i][j].visited = false;
			}
		}
	}
	
	private boolean connectionType(Direction direction, Room room) {
		for(int i = 0; i < room.doors.length; i++) {
			if (room.doors[i].dir == direction) {
				return room.doors[i].isWall;
			}
		}
		//Else return wall
		return true;
	}
	
	public void printMaze() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				int rVal  = roomToInt(rooms[i][j]);
				System.out.print(Integer.toHexString(rVal));				
			}
			System.out.println();
		}		
	}

	private int roomToInt(Room r) {
		int result = 0;
		for(int i = 0; i < r.doors.length; i++) {
			Door d = r.doors[i];
			switch (d.dir) {
			case DOWN:
				result += 2 * (d.isWall ? 1 : 0);
				break;
			case LEFT:
				result += 4 * (d.isWall ? 1 : 0);
				break;
			case RIGHT:
				result += 8 * (d.isWall ? 1 : 0);
				break;
			case UP:
				result += 1 * (d.isWall ? 1 : 0);
				break;
			default:
				break;			
			}
		}
		return result;
		
	}
	
	/**
	 * Guarantees a path from any room in the maze to any other.
	 * But only in two dimensions.
	 * :)
	 * @param x
	 * @param y
	 */
	private void drunkenWalk(int x, int y) {
		Room r = rooms[y][x];
		r.visited = true;
		Util.shuffleArray(r.doors);
		for(int i = 0; i < r.doors.length; i++) {
			Door d = r.doors[i];
			boolean outOfBounds = false;
			Room neighbor;
			Direction oppositeDir;
			int neighborX = x;
			int neighborY = y;
			switch(d.dir) {
			case LEFT:
				oppositeDir = Direction.RIGHT;
				neighborX--;
				if (neighborX < 0) {
					outOfBounds = true;
				}
				break;
			case RIGHT:
				oppositeDir = Direction.LEFT;
				neighborX++;
				if (neighborX >= cols) {
					outOfBounds = true;
				}
				break;
			case UP:
				oppositeDir = Direction.DOWN;
				neighborY--;
				if (neighborY < 0) {
					outOfBounds = true;
				}
				break;
			case DOWN:
				oppositeDir = Direction.UP;
				neighborY++;
				if (neighborY >= rows) {
					outOfBounds = true;
				}
				break;
			default:
				oppositeDir = Direction.UP;
				outOfBounds = true;
				break;
			
			}
			

			
			if (outOfBounds) {
				d.isWall = true;
			} else {
				neighbor = rooms[neighborY][neighborX];
				if (!neighbor.visited) {
					d.isWall = false;
					drunkenWalk(neighborX, neighborY);
				} else {
					d.isWall = connectionType(oppositeDir, neighbor);
				}
			}
		}
		
	}

}
