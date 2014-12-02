package activity;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Render extends Canvas{
	
	public final static int TILE_SIZE = 9; // MUST BE A MULTIPLE OF 3
	public final static Color WALL_COLOR = Color.black;
	public final static Color PATH_COLOR = Color.gray;
	public final static Color WALKER_COLOR = Color.white;
	public final static int WALL = 0x000000;
	public final static int PATH = 0x7f7f7f;
	public final static int WALKER = 0xffffff;
	
	public static int buffer = 3;
	private Maze maze;
	private int[][] pixels;
	//private int currentX, currentY;
	
	public Render(Maze maze) {
		this.maze = maze;
		pixels = new int[maze.cols * TILE_SIZE][maze.rows * TILE_SIZE];
		fillImage();
		generateImage();
		//currentX = 0;
		//currentY = 0;
	}
	
	/*public Render(Maze maze, int currentX, int currentY) {
		this.maze = maze;
		pixels = new int[maze.cols * TILE_SIZE][maze.rows * TILE_SIZE];
		pixelsToShow = new int[maze.cols * TILE_SIZE][maze.rows * TILE_SIZE];
		fillImage();
		generateImage();
		this.currentX = currentX;
		this.currentY = currentY;
	}*/
	
	public void fillImage() {
		for (int x = 0; x < pixels.length; x++)
			for (int y = 0; y < pixels[0].length; y++)
				pixels[x][y] = WALL;
	}
	
	public void generateImage() {
		for (int tileX = 0; tileX < maze.cols; tileX++) {
			for (int tileY = 0; tileY < maze.rows; tileY++) {
				for (int x = 0; x < TILE_SIZE; x++) {
					for (int y = 0; y < TILE_SIZE; y++) {
						if ((x < 3 || x >= 6) && (y < 3 || y >= 6));
						else if ((x < 3) && (y >= 3 || y < 6)) {//LEFT
							if (!maze.rooms[tileX][tileY].doors[0].isWall)
								pixels[x + (tileX * TILE_SIZE)][y + (tileY * TILE_SIZE)] = PATH;
						}
						else if ((x >= 6) && (y >= 3 || y < 6)) {//RIGHT
							if (!maze.rooms[tileX][tileY].doors[1].isWall)
								pixels[x + (tileX * TILE_SIZE)][y + (tileY * TILE_SIZE)] = PATH;
						}
						else if ((x >= 3 || x < 6) && (y < 3)) {//UP
							if (!maze.rooms[tileX][tileY].doors[2].isWall)
								pixels[x + (tileX * TILE_SIZE)][y + (tileY * TILE_SIZE)] = PATH;
						}
						else if ((x >=3 || x < 6) && (y >= 6)) {//DOWN
							if (!maze.rooms[tileX][tileY].doors[3].isWall)
								pixels[x + (tileX * TILE_SIZE)][y + (tileY * TILE_SIZE)] = PATH;
						}
						else
							pixels[x + (tileX * TILE_SIZE)][y + (tileY * TILE_SIZE)] = PATH;
					}
				}
			}
		}
	}
	
	public void renderImage(int currentX, int currentY) {
		BufferedImage image = new BufferedImage(maze.cols * TILE_SIZE, maze.rows * TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		int[] pixelsToShow = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		int [][] pixelsToShowMatrix = pixels.clone();
		for (int x = currentX + (TILE_SIZE / 3); x < currentX + 2 * (TILE_SIZE / 3); x++)
			for (int y = currentY + (TILE_SIZE / 3); y < currentY + 2 * (TILE_SIZE / 3); y++)
				pixelsToShowMatrix[x][y] = WALKER;
		
		for (int x = 0; x < pixelsToShowMatrix.length; x++)
			for (int y = 0; y < pixelsToShowMatrix[0].length; y++)
				pixelsToShow[x + pixelsToShowMatrix.length * y] = pixelsToShowMatrix[x][y];
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(buffer);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(WALL_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		//g.fillRect(Mouse.getX(), Mouse.getY(), 64, 64);
		g.dispose();
		bs.show();
	}
	
	/*public void paint(Graphics g) {
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
				//if (currentX == x && currentY == y)
				//	g.setColor(WALKER_COLOR);
				//g.fillRect(x * TILE_SIZE + (TILE_SIZE / 3), y * TILE_SIZE + (TILE_SIZE / 3), TILE_SIZE / 3, TILE_SIZE / 3);
			}
		}
		
	}*/
	
	
}
