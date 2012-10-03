package control;

import java.util.Random;

public class Learner {
	Random random;
	
	public Learner() {
		random = new Random();
	}
	
	public Move getNextMove() {
		return Move.values()[random.nextInt(Move.values().length)];
	}
}
