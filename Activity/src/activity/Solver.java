package activity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import activity.Door.Direction;

public class Solver implements KeyListener {
	Maze maze;
	int solverX;
	int solverY;
	boolean useDirection = false;
	Direction userDirection;
	
	Solver(Maze maze) {
		this.maze = maze;
	}
		
	public void addToList(int x, int y) {
		addToList(maze.rooms[y][x]);		
	}

	
	public void addToList(Room r) {
		System.out.println(String.format("Room (%d, %d)", r.x, r.y));
		return;
	}
	
	public boolean depthFirstSearch(int x, int y, int goalX, int goalY) {
		solverX = x;
		solverY = y;

		Room current = maze.rooms[y][x];
		addToList(current);
		
		if (x == goalX && y == goalY) {
			return true;
		}
		
		current.visited = true;
		//So remember the doors were randomly sorted here.
		//If there is more than two ways to search (back and forward), we'll prompt the user for input.
		//Depending on what the decision is and how we're feeling, we'll add the user's choice to the front.
		int numOptions = 0;
		for(int i = 0; i < current.doors.length; i++) {
			numOptions += current.doors[i].isWall ? 0 : 1;
		}
		
		
		if (numOptions > 2) {
			//TODO: prompt user input.
		}
		
		if (useDirection) {
			prioritizeDirection(userDirection, current.doors);
			useDirection = false;
		}
		
		for(int i = 0; i < current.doors.length; i++) {
			Door d = current.doors[i];
			int neighborX = x;
			int neighborY = y;
			switch (d.dir) {
			case LEFT:
				neighborX--;
				if (neighborX < 0 && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening LEFT\n", x, y));
				}
				break;
			case RIGHT:
				neighborX++;
				if (neighborX >= maze.cols && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening RIGHT\n", x, y));
				}
				break;
			case UP:
				neighborY--;
				if (neighborY < 0 && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening UP\n", x, y));
				}
				break;
			case DOWN:
				neighborY++;
				if (neighborY >= maze.rows && !d.isWall) {
					System.out.println(String.format("Room at (%d, %d) has an opening UP\n", x, y));
				}
				break;
			default:
				break;				
			}
			
			
			if (!d.isWall) {
				if (!maze.rooms[neighborY][neighborX].visited) {
					if (depthFirstSearch(neighborX, neighborY, goalX, goalY)) {
						return true;
					} else {
						addToList(x, y);
					}
				}
			}			
		}
		//End for loop
		//TODO: frustration!
		return false;
	}

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

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			useDirection = true;
			userDirection = Direction.UP;
			break;
		case KeyEvent.VK_DOWN:
			useDirection = true;
			userDirection = Direction.DOWN;
			break;
		case KeyEvent.VK_LEFT:
			useDirection = true;
			userDirection = Direction.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			useDirection = true;
			userDirection = Direction.RIGHT;
			break;
		default:
			useDirection = false;
			break;
		}
	}
}
