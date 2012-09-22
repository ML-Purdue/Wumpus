package control;
import java.util.Scanner;
import environment.Direction;
import environment.Engine;
import environment.AgentStatus;

public class CommandLineInterface {
	Scanner sc;
	Engine game;
	
	public CommandLineInterface (Engine game) {
		sc = new Scanner(System.in);
		this.game = game;

		/*
		System.out.println("Welcome to the Wumpus game.  Please enter the board size");
		int width = Integer.parseInt(sc.nextLine());
		System.out.println("Generating a board of width " + width);
		*/
	}

	public static String dirToStr(Direction dir) {
		switch(dir){
		case north :
			return "North";
		case south :
			return "South";
		case east :
			return "East";
		case west :
			return "West";
		}
		// This shouldn't happen
		return null;
	}

	public void printStatus () {
		System.out.println("Turn\t" + game.getTurn());
		System.out.println(">>> Visible Game Board <<<");
		game.draw();
		System.out.println(">>> Player Info <<<");
		System.out.println("Tile:\t" + game.getX() + ", " + game.getY());
		System.out.println("Facing:\t" + dirToStr(game.getHeading()));
		System.out.println("Arrow:\t" + game.holdingArrow());
		System.out.println("Gold:\t" + game.holdingGold());
		System.out.println(">>> Tile Info <<<");
		System.out.println("Windy:\t" + game.isWindy());
		System.out.println("Stinky:\t" + game.isStinky());
		System.out.println("Gold:\t" + game.hasGold());
		System.out.println(">>> Options <<<");
	}
	
	public void printScore() {
		System.out.println("Game Over!");
		System.out.println("Entire game board:");
		game.draw();
		System.out.println("Alive:\t" + 
				(game.getAgentStatus() == AgentStatus.ESCAPED));
		System.out.println("Gold:\t" + game.holdingGold());
		System.out.println("Arrow:\t" + game.holdingArrow());
		System.out.println("Steps:\t" + game.getTurn());
		System.out.println("Total score: " + game.getScore());
	}

	/**    _        _   _                 
	 *    / \   ___| |_(_) ___  _ __  ___ 
	 *   / _ \ / __| __| |/ _ \| '_ \/ __|
	 *  / ___ \ (__| |_| | (_) | | | \__ \
	 * /_/   \_\___|\__|_|\___/|_| |_|___/
	 * 
	 * Return true if the game state has changed
	 */
	public boolean turn (){
		while (true) {
			System.out.println("[N]orth, [S]outh, [E]ast, [W]est, [B]ack");
			String line = sc.nextLine().toUpperCase();
			if (line.length() == 0)
				continue;
			
			switch (line.charAt(0)) {
			case 'N' :
				return game.turn(Direction.north);
			case 'S' :
				return game.turn(Direction.south);
			case 'E' :
				return game.turn(Direction.east);
			case 'W' :
				return game.turn(Direction.west);
			case 'B' :
				return false;
			default :
				break;
			}
		}
	}

	public boolean forward() {
		boolean result = game.forward();
		if (result)
			System.out.println("Moved " + dirToStr(game.getHeading()) + " one tile.");
		else
			System.out.println("Could not move " + dirToStr(game.getHeading()) + ".");
		
		if (game.getAgentStatus() == AgentStatus.EATEN) {
			System.out.println("You were eaten by the Wumpus!");
		} else if (game.getAgentStatus() == AgentStatus.FALLEN) {
			System.out.println("You fell down a bottomless pit!");
		}
		return true;  // The game state has changed (turn ticker)
	}

	public boolean grab() {
		boolean result = game.grab();
		if (result)
			System.out.println("Grabbed the gold!");
		else
			System.out.println("There's nothing to grab.");
		return true;  // The game state has changed (turn ticker)
	}

	public boolean shoot() {
		if (!game.holdingArrow()) {
			System.out.println("You already shot your arrow!");
			return false;
		}
		boolean result = game.shoot();
		if (result)
			System.out.println("You hear a terrible scream, and you know the Wumpus is dead.");
		else
			System.out.println("You hear the arrow clang off a wall.");
		return true;
	}

	public boolean escape() {
		boolean result = game.escape();
		if (result)
			System.out.println("You have escaped from the Wumpus Cavern.");
		else
			System.out.println("You are unable to escape (you need to be in tile 0,0 to do that).");
		return true;  // The game state has changed (turn ticker)
	}
	
	public void iterate() {
		printStatus();
		boolean changed = false;
		while (!changed) {
			System.out.println("[T]urn, [F]orward, [S]hoot, [G]rab, [E]scape");
			String line = sc.nextLine().toUpperCase();
			if (line.length() == 0)
				continue;
			
			switch (line.charAt(0)) {
			case 'T' :
				changed = turn();
				break;
			case 'F' :
				changed = forward();
				break;
			case 'S' :
				changed = shoot();
				break;
			case 'G' :
				changed = grab();
				break;
			case 'E' :
				changed = escape();
				break;
			case 'P' :
				game.draw();
				changed = false;
				break;
			default :
				changed = false;
				break;
			}
		}
	}

	public static void main(String[] args) {
		int width = 4;
		Engine game = new Engine(width);
		CommandLineInterface cli = new CommandLineInterface(game);

		while (game.inProgress()) {
			cli.iterate();
		}
		
		cli.printScore();
	}
}
