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
		
		Maze m = new Maze(width, height);
		Solver s = new Solver(m);

		Random r = new Random();
		s.depthFirstSearch(0, 0, r.nextInt(width), r.nextInt(height));
		
		/*For reference.
		* http://cs.brown.edu/courses/cs033/docs/projects/maze_generator.pdf
		* http://cs.brown.edu/courses/cs033/docs/projects/maze_solver.pdf
		*/
		
	}

}
