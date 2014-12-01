package activity;

import java.awt.*;
import java.applet.*;

public class Render extends Applet {
	
	private Maze maze;
	public final static int TILE_SIZE = 9; // MUST BE A MULTIPLE OF 3
	public final static Color WALL_COLOR = Color.black;
	public final static Color PATH_COLOR = Color.gray;
	public final static Color WALKER_COLOR = Color.white;
	private int currentX, currentY;
	
	public Render(Maze maze, int currentX, int currentY) {
		this.maze = maze;
		this.currentX = currentX;
		this.currentY = currentY;
	}
	
	public void init() {
		setBackground(Color.BLACK);
	}
	
	public void paint(Graphics g) {
		for (int x = 0; x < maze.cols; x++) {
			for (int y = 0; y < maze.rows; y++) {
				g.setColor(WALL_COLOR);
				g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				//g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE / 3, TILE_SIZE / 3);
				//g.drawRect(x * TILE_SIZE + (2 * (TILE_SIZE / 3)), y * TILE_SIZE, TILE_SIZE / 3, TILE_SIZE / 3);
				//g.drawRect(x * TILE_SIZE + (2 * (TILE_SIZE / 3)), y * TILE_SIZE + (2 * (TILE_SIZE / 3)), TILE_SIZE / 3, TILE_SIZE / 3);
				//g.drawRect(x * TILE_SIZE, y * TILE_SIZE + (2 * (TILE_SIZE / 3)), TILE_SIZE / 3, TILE_SIZE / 3);
				g.setColor(PATH_COLOR);
				if (!maze.rooms[x][y].doors[0].isWall) //LEFT
					g.fillRect(x * TILE_SIZE, y * TILE_SIZE + (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3);
				if (!maze.rooms[x][y].doors[1].isWall) //RIGHT
					g.fillRect(x * TILE_SIZE + (2 * (TILE_SIZE / 3)), y * TILE_SIZE + (TILE_SIZE / 3), TILE_SIZE, TILE_SIZE);
				if (!maze.rooms[x][y].doors[2].isWall) //UP
					g.fillRect(x * TILE_SIZE + (TILE_SIZE / 3), y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				if (!maze.rooms[x][y].doors[3].isWall) //DOWN
					g.fillRect(x * TILE_SIZE + (TILE_SIZE / 3), y * TILE_SIZE + (2 * (TILE_SIZE / 3)), TILE_SIZE, TILE_SIZE);
				if (currentX == x && currentY == y)
					g.setColor(WALKER_COLOR);
				g.fillRect(x * TILE_SIZE + (TILE_SIZE / 3), y * TILE_SIZE + (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3);
			}
		}
	}
}
