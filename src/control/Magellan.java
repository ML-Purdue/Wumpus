package control;

import java.awt.Point;
import java.util.List;
import java.util.Stack;

import environment.Direction;
import environment.Engine;

public class Magellan extends Agent {
	Learner learner;
	Stack<Step> steps;
	
	private class Step {
		Direction direction;
		Point point;
		
		public Step(Direction direction, Point point) {
			this.direction = direction;
			this.point = point;
		}
		
		public boolean equals(Object other) {
			return this.point.equals(((Step)other).point);
		}
		
		public String toString() {
			return direction + " (" + point.x + ", " + point.y + ")\n";
		}
	}
	
	public Magellan(Engine game) {
		super(game);
		learner = new Learner();
		steps = new Stack<Step>();
	}

	public void iterate() {
		if (!game.holdingGold()) {
			Move move = learner.getNextMove();
			switch (move) {
			case forward:
				if (game.forward()) {
					Step step = new Step(getReverse(game.getHeading()), new Point(game.getX(), game.getY()));
					if (steps.contains(step)) {
						while (steps.indexOf(step) < steps.size() - 1) {
							steps.pop();
						}
					} else {
						steps.push(step);
					}
				}
				break;
			case north:
				game.turn(Direction.NORTH);
				break;
			case south:
				game.turn(Direction.SOUTH);
				break;
			case east:
				game.turn(Direction.EAST);
				break;
			case west:
				game.turn(Direction.WEST);
				break;
			case grab:
				game.grab();
				break;
			case shoot:
				game.shoot();
				break;
			}
			//game.draw();
			//System.out.println();
		} else {
			if (steps.size() == 0) {
				game.escape();
				//game.draw();
				//System.out.println();
				return;
			}
			
			Step step = steps.pop();
			if (step.direction != game.getHeading()) {
				switch (step.direction) {
				case NORTH:
					game.turn(Direction.NORTH);
					break;
				case SOUTH:
					game.turn(Direction.SOUTH);
					break;
				case EAST:
					game.turn(Direction.EAST);
					break;
				case WEST:
					game.turn(Direction.WEST);
					break;
				}
			}
			
			game.forward();
			//game.draw();
			//System.out.println();
		}
	}
	
	private boolean isTurn(Move move) {
		return move == Move.east || move == Move.west || move == Move.south || move == Move.north;
	}
	
	private Direction getReverse(Direction heading) {
		switch (heading) {
		case NORTH: return Direction.SOUTH;
		case EAST: return Direction.WEST;
		case SOUTH: return Direction.NORTH;
		case WEST: return Direction.EAST;
		}
		return null;
	}

	public void end() {
		//System.out.println("END\n\n\n\n");
	}
}
