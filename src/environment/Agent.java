package environment;

public class Agent {
    public Direction heading;
    public Tile location;
    public AgentStatus status;
    public boolean hasGold;
    public boolean hasArrow;
    
    public Agent(Tile location) {
    	this.location = location;
    	this.heading = Direction.north;
    	this.status = AgentStatus.ALIVE;
    	this.hasGold = false;
    	this.hasArrow = true;
    }
}

