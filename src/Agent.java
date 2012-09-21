
public class Agent {
	public Tile location;
    public Direction heading;
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

