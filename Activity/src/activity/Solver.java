package activity;

import activity.Door.Direction;

public class Solver {
	Maze maze;
	int solverX, goalX;
	int solverY, goalY;
	boolean useDirection = false;
	Direction userDirection;
	public static int ticks = 0;
	public static final int tickLimit = 100;
	
	Solver(Maze maze, int startX, int startY, int goalX, int goalY) {
		this.maze = maze;
		this.solverX = startX;
		this.solverY = startY;
		this.goalX = goalX;
		this.goalY = goalY;
	}
	
	public boolean tick(Direction d) {
		this.useDirection = true;
		this.userDirection = d;
		return tick();
	}
	
	public boolean tick() {
		Room current = maze.rooms[solverY][solverX];
		
		if (solverX == goalX && solverY == goalY) {
			return true;
		}
		
		int numOptions = 0;
		for(int i = 0; i < current.doors.length; i++) {
			numOptions += current.doors[i].isWall ? 0 : 1;
		}
		
		if (useDirection) {
			prioritizeDirection(userDirection, current.doors);
			useDirection = false;
		}
		
		int doorIndex = -1;
		for (int i = 0; i < current.doors.length; i++) {
			if (!current.doors[i].isWall) {
				doorIndex = i;
				break;
			}
		}
		
		if (doorIndex == -1) {
			return false;
		}
		else {
			Door d = current.doors[doorIndex];
			int neighborX = solverX;
			int neighborY = solverY;
			switch (d.dir) {
			case LEFT:
				neighborX--;
				if (neighborX < 0 && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening LEFT\n", solverX, solverY));
				}
				break;
			case RIGHT:
				neighborX++;
				if (neighborX >= maze.cols && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening RIGHT\n", solverX, solverY));
				}
				break;
			case UP:
				neighborY--;
				if (neighborY < 0 && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening UP\n", solverX, solverY));
				}
				break;
			case DOWN:
				neighborY++;
				if (neighborY >= maze.rows && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening UP\n", solverX, solverY));
				}
				break;
			default:
				break;				
			}
			
			solverX = neighborX;
			solverY = neighborY;
		}
		
		return false;
	}
//	public boolean depthFirstSearch(int x, int y, int goalX, int goalY) {
//		solverX = x;
//		solverY = y;
//
//		Room current = maze.rooms[y][x];
//		addToList(current);
//		
//		if (x == goalX && y == goalY) {
//			return true;
//		}
//		
//		current.visited = true;
//		//So remember the doors were randomly sorted here.
//		//If there is more than two ways to search (back and forward), we'll prompt the user for input.
//		//Depending on what the decision is and how we're feeling, we'll add the user's choice to the front.
//		int numOptions = 0;
//		for(int i = 0; i < current.doors.length; i++) {
//			numOptions += current.doors[i].isWall ? 0 : 1;
//		}
//		
//		
//		if (numOptions > 2) {
//			//TODO: prompt user input.
//		}
//		
//		if (useDirection) {
//			prioritizeDirection(userDirection, current.doors);
//			useDirection = false;
//		}
//		
//		for(int i = 0; i < current.doors.length; i++) {
//			Door d = current.doors[i];
//			int neighborX = x;
//			int neighborY = y;
//			switch (d.dir) {
//			case LEFT:
//				neighborX--;
//				if (neighborX < 0 && !d.isWall) {
//					System.out.println(String.format("Room at (%d, %d) has an opening LEFT\n", x, y));
//				}
//				break;
//			case RIGHT:
//				neighborX++;
//				if (neighborX >= maze.cols && !d.isWall) {
//					System.out.println(String.format("Room at (%d, %d) has an opening RIGHT\n", x, y));
//				}
//				break;
//			case UP:
//				neighborY--;
//				if (neighborY < 0 && !d.isWall) {
//					System.out.println(String.format("Room at (%d, %d) has an opening UP\n", x, y));
//				}
//				break;
//			case DOWN:
//				neighborY++;
//				if (neighborY >= maze.rows && !d.isWall) {
//					System.out.println(String.format("Room at (%d, %d) has an opening UP\n", x, y));
//				}
//				break;
//			default:
//				break;				
//			}
//			
//			
//			if (!d.isWall) {
//				if (!maze.rooms[neighborY][neighborX].visited) {
//					if (depthFirstSearch(neighborX, neighborY, goalX, goalY)) {
//						return true;
//					} else {
//						addToList(x, y);
//					}
//				}
//			}			
//		}
//		//End for loop
//		//TODO: frustration!
//		return false;
//	}

	private void prioritizeDirection(Direction d, Door[] doors) {
		Door first = doors[0];
		for(int i = 0; i < doors.length; i++) {
			Door current = doors[i];
			if (current.dir == d && !current.isWall) {
				//Swap current and first.
				if (Util.listenToUser()) {
					doors[0] = current;
					doors[i] = first;
				}
			}
		}
	}
}
