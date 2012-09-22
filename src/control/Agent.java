package control;
import environment.Engine;

public abstract class Agent {
	Engine game;
	
	public Agent (Engine game) {
		this.game = game;
	}
	
	/* Make the next move */
	public abstract void iterate();
	/* Handle the end of the game (Learning, printing a score, etc) */
	public abstract void end();
}
