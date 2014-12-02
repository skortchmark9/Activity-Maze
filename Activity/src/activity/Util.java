package activity;

public class Util {
	
	public static <A> void shuffleArray(A[] array) {
		return;
	}
/*		Random r = new Random();
		for(int i = 0; i < array.length; i++) {
			A tmp = array[i];
			int swap = r.nextInt() % (array.length - 1);
			array[i] = array[swap];
			array[swap] = tmp;			
		}
	}*/

	public static boolean listenToUser() {
		return Math.random() <= Constants.ListenToUser;
	}

}
