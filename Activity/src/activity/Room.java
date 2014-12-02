package activity;

import java.awt.Point;
import java.util.Random;

public class Room extends Point {
	
	private static final long serialVersionUID = 1L;
	Door[] doors;
	boolean visited = false;
	
	Room(int x, int y, int hex) {
		this.x = x;
		this.y = y;
		if (hex >= 16) {
			System.out.println("BAD HEX VALUE");
		}
		doors = Room.initDoors(true);
		for(int i = 0; i < doors.length; i++) {
			switch (doors[i].dir) {
			case RIGHT:
				doors[i].isWall = (hex & 0x8) != 0;
				break;
			case LEFT:
				doors[i].isWall = (hex & 0x4) != 0;
				break;
			case DOWN:
				doors[i].isWall = (hex & 0x2) != 0;
				break;
			case UP:
				doors[i].isWall = (hex & 0x1) != 0;
				break;
			default:
				break;			
			}
		}
		
		
		
	}
	
	Room(int x, int y) {
		this(x, y, false);
	}
	
	Room(int x, int y, boolean OnlyWalls) {
		super(x, y);
		this.doors = Room.initDoors(OnlyWalls);
	}


	private static Door[] initDoors(boolean onlyWalls) {
		Door[] doors = new Door[Constants.NUM_DIRECTIONS];
		doors[0] = new Door();
		doors[0].dir = Door.Direction.LEFT;
		doors[1] = new Door();
		doors[1].dir = Door.Direction.RIGHT;
		doors[2] = new Door();
		doors[2].dir = Door.Direction.UP;
		doors[3] = new Door();
		doors[3].dir = Door.Direction.DOWN;

		Random r = new Random();
		for(int i = 0; i < Constants.NUM_DIRECTIONS; i++) {
			doors[i].isWall = true;//= onlyWalls;
					//|| (r.nextInt() % Constants.WALL_FREQUENCY) == 0;
		}
		
		return doors;
	}

}
