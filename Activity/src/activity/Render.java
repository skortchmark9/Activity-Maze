package activity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Render {
	
	public final static int TILE_SIZE = 9 * 5; // MUST BE A MULTIPLE OF 3
	public final static Color WALL_COLOR = Color.black;
	public final static Color PATH_COLOR = Color.gray;
	public final static Color WALKER_COLOR = Color.white;
	public Maze maze;
	//private int currentX, currentY;
	
	public Render(Maze maze) {
		this.maze = maze;
		//currentX = 0;
		//currentY = 0;
	}
	
	/*public Render(Maze maze, int currentX, int currentY) {
		this.maze = maze;
		this.currentX = currentX;
		this.currentY = currentY;
	}*/
	/*
	 * 0 - none
	 * 1 - up
	 * 2 - down
	 * 3 - up, down
	 * 4 - left
	 * 5 - left, up
	 * 6 - left, down
	 * 7 - up, down, left
	 * 8 - right
	 * 9 - up, right
	 * 10 - down, right
	 * 11 - up, down, right
	 * 12 - left, right
	 * 13 - left, right, up
	 * 14 - left, right, down
	 * 15 - all
	 */
	public void paintRoom(Room r, Graphics g, boolean isWalker) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(WALL_COLOR);
		for (int i = 0; i < 9; i++)
			g2d.fill3DRect(r.x * TILE_SIZE + (i % 3) * (TILE_SIZE / 3), r.y * TILE_SIZE + (i / 3) * (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3, true);
		g2d.setColor(PATH_COLOR);
		int numOpenings = 0;
		if (!r.doors[0].isWall) {//LEFT 
			g2d.fill3DRect(r.x * TILE_SIZE, r.y * TILE_SIZE + (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3, true);
			numOpenings++;
		}
		if (!r.doors[1].isWall) {//RIGHT
			g2d.fill3DRect(r.x * TILE_SIZE + (2 * (TILE_SIZE / 3)), r.y * TILE_SIZE + (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3, true);
			numOpenings++;
		}
		if (!r.doors[2].isWall) {//UP
			g2d.fill3DRect(r.x * TILE_SIZE + (TILE_SIZE / 3), r.y * TILE_SIZE, TILE_SIZE / 3, TILE_SIZE / 3, true);
			numOpenings++;
		}
		if (!r.doors[3].isWall) {//DOWN
			g2d.fill3DRect(r.x * TILE_SIZE + (TILE_SIZE / 3), r.y * TILE_SIZE + (2 * (TILE_SIZE / 3)), TILE_SIZE / 3, TILE_SIZE / 3, true);
			numOpenings++;
		}
		
		if (numOpenings >= 2) {
			int i = 4;
			g2d.fill3DRect(r.x * TILE_SIZE + (i % 3) * (TILE_SIZE / 3), r.y * TILE_SIZE + (i / 3) * (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3, true);
		}
		
		if (isWalker) {
			g2d.setColor(WALKER_COLOR);
			int i = 4;
			g2d.fill3DRect(r.x * TILE_SIZE + (i % 3) * (TILE_SIZE / 3), r.y * TILE_SIZE + (i / 3) * (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3, true);
		}
	}
	
	public void paint(Graphics g, int currentX, int currentY) {
		for (int x = 0; x < maze.cols; x++) {
			for (int y = 0; y < maze.rows; y++) {
				paintRoom(maze.rooms[y][x], g, (currentX == x && currentY == y));
			}
		}
	}
}