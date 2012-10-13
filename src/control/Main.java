package control;

import environment.Engine;

public class Main {
	
	public static void printUsage() {
		String usage = "";
		usage += "Main: testing framework for agents\n";
		usage += "Arguments: width runs\n";
		
		System.out.print(usage);
	}
	
	public static void printStats(int[] scores) {
		int best = -1000000;
		int worst = 1000000;
		int mean = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] > best) {
				best = scores[i];
			}
			if (scores[i] < worst) {
				worst = scores[i];
			}
			mean += scores[i];
		}
		mean /= scores.length;
		
		String stats = "";
		stats += "Test completed\n";
		stats += "Number of iterations: " + scores.length + "\n";
		stats += "Best score: " + best + "\n";
		stats += "Worst score: " + worst + "\n";
		stats += "Mean score: " + mean + "\n";
		System.out.print(stats);
	}

	/**
	 * Arguments: 
	 */
	public static void main(String[] args) {
		int width = -1;
		int runs = -1;
		int[] scores;
		
		if (args.length < 2) {
			printUsage();
			System.exit(127);
		}
		
		try {
			width = Integer.parseInt(args[0]);
			runs = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			printUsage();
			System.exit(127);
		}
		
		scores = new int[runs];
		
		// TODO: Replace with:
		// Agent player = new <your agent class>(game);
		for (int i = 0; i < runs; i++) {
			Engine game = new Engine(width);
<<<<<<< HEAD
			Agent player = new Magellan(game);
=======
			Agent player = new AndrewBot(game);//new CommandLineInterface(game);
>>>>>>> 2ce23e1064bdfcf96de851f538d908b7302b494d

			while (game.inProgress()) {
				player.iterate();
			}

			player.end();
			scores[i] = game.getScore();
		}
		
		printStats(scores);

	}

}
