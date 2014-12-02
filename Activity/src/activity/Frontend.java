package activity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import activity.Door.Direction;

public class Frontend implements KeyListener  {
	Solver s;
	Canvas c;
	boolean useDirection = false;
	Direction userDirection;
	
	Frontend(Solver s) {
		this.s = s;
		JFrame frame = new JFrame("Maze");
//		frame.setUndecorated(true);
//		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setPreferredSize(new Dimension(Render.TILE_SIZE * s.maze.cols, Render.TILE_SIZE * s.maze.rows));

		c = new Canvas(s);
		
		frame.addKeyListener(this);
		frame.getContentPane().add(c);

		frame.pack();
		frame.setVisible(true);
		
		start();
		
//      GraphicsEnvironment.getLocalGraphicsEnvironment()
//      .getDefaultScreenDevice()
//      .setFullScreenWindow(frame);


	}
	
	public void start() {
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setWalker(s.solverX, s.solverY);
			c.repaint();
			if (useDirection) {
				done = s.tick(userDirection);
				System.out.println("USING A DIRECTION");
				System.out.println(userDirection);
				useDirection = false;
			} else {
				done = s.tick();
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
	
class Canvas extends JPanel {
	private static final long serialVersionUID = -399237770170496514L;
	int x;
	int y;
	Render r;
	
	Canvas(Solver s) {
		super();
		this.r = new Render(s.maze, s.solverX, s.solverY, s.goalX, s.goalY);
	}
	
	public void setWalker(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		r.paint(g, x, y);
	}

}
