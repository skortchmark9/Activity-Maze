package activity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frontend {
	Render r;
	
	Frontend(Render r) {
		this.r = r;

		JFrame frame = new JFrame("Maze");
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

		Canvas p = new Canvas(r);
		frame.getContentPane().add(p);

		frame.pack();
		frame.setVisible(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .setFullScreenWindow(frame);

	}
}
	
class Canvas extends JPanel {
	Render r;
	
	Canvas(Render r) {
		super();
		this.r = r;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		r.paint(g);
	}

}
