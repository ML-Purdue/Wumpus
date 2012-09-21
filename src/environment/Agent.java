package environment;

public class Agent {
    public Direction heading;
    public Tile location;
    public boolean alive;
    public boolean hasGold;
    public boolean hasArrow;
    
    public Agent(Tile location) {
    	this.location = location;
    	this.heading = Direction.north;
    	this.alive = true;
    	this.hasGold = false;
    	this.hasArrow = true;
    }
}

