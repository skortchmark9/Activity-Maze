package activity;

import java.util.Random;

public class Util {
	
	public static <A> void shuffleArray(A[] array) {
		Random r = new Random();
		for(int i = 0; i < array.length; i++) {
			A tmp = array[i];
			int swap = r.nextInt(array.length);
			array[i] = array[swap];
			array[swap] = tmp;			
		}
	}

	public static boolean listenToUser() {
		return true;
//		return Math.random() <= Constants.ListenToUser;
	}
	
	public static void printMaze(Maze m) {
		for(int i = 0; i < m.rows; i++) {
			for(int j = 0; j < m.cols; j++) {
				
			}
		}		
	}
	
	public static void printRoom(Room r) {
		
	}

}
