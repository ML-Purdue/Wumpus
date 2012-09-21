import java.util.Scanner;

public class CommandLineInterface {
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

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to the Wumpus game.  Please enter the board size");
		int width = sc.nextInt();
		System.out.println("Generating a board of width " + width);
		
		Engine game = new Engine(width);
		while (game.inProgress()) {
			System.out.println("Turn\t" + game.getTurn());
			System.out.println("Location:\t" + game.getX() + ", " + game.getY());
			System.out.println("Facing:\t" + dirToStr(game.getHeading()));
			System.out.println(">>> Tile Info <<<");
			System.out.println("Windy:\t" + game.isWindy());
			System.out.println("Stinky:\t" + game.isStinky());
			System.out.println("Treasure\t" + game.hasGold());
		}

	}

}
