package activity;

import java.util.Random;


public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: <maze-width> <maze-height>");
		}
		
		int width, height;
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Could not parse args");
			return;
		}

		Random r = new Random();
		Maze m = new Maze("mazes/maze" + r.nextInt(10) + ".txt", 10, 25);
		
		Solver s = new Solver(m, 0, 0, 24, 9);
		
		
		new Frontend(s);

		
//		s.depthFirstSearch(0, 0, r.nextInt(width), r.nextInt(height));
		
		/*For reference.
		* http://cs.brown.edu/courses/cs033/docs/projects/maze_generator.pdf
		* http://cs.brown.edu/courses/cs033/docs/projects/maze_solver.pdf
		*/
		
	}

}
