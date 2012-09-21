package control;
import java.util.Scanner;
import environment.Direction;
import environment.Engine;

public class CommandLineInterface {
	static Scanner sc;
	static Engine game;
	
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
	
	public static void printStatus () {
		System.out.println("Turn\t" + game.getTurn());
		System.out.println(">>> Player Info <<<");
		System.out.println("Location:\t" + game.getX() + ", " + game.getY());
		System.out.println("Facing:\t" + dirToStr(game.getHeading()));
		System.out.println("Has Arrow:\t" + game.hasArrow());
		System.out.println("Has Gold:\t" + game.hasGold());
		System.out.println(">>> Tile Info <<<");
		System.out.println("Windy:\t" + game.isWindy());
		System.out.println("Stinky:\t" + game.isStinky());
		System.out.println("Treasure\t" + game.hasGold());
		System.out.println(">>> Options <<<");
		
	}
	
	public static boolean turn (){
		boolean valid = false;
		while (!valid) {
			System.out.println("[N]orth, [S]outh, [E]ast, [W]est, [B]ack");
			String line = sc.nextLine();
			switch (line.charAt(0)) {
			case 'N' :
			case 'S' :
			case 'E' :
			case 'W' :
			case 'B' :
			default :
				valid = false;
				break;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		
		System.out.println("Welcome to the Wumpus game.  Please enter the board size");
		int width = sc.nextInt();
		System.out.println("Generating a board of width " + width);
		game = new Engine(width);
		
		while (game.inProgress()) {
			printStatus();
			boolean valid = false;
			while (!valid) {
				System.out.println("[T]urn, [M]ove, [S]hoot, [G]rab, [E]scape");
				String line = sc.nextLine();
				switch (line.charAt(0)) {
				case 'T' :
					valid = turn();
					break;
					
				
				}
			}
			
		}

	}

}
