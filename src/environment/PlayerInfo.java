package environment;

public class PlayerInfo {
    public Direction heading;
    public Tile location;
    public Status status;
    public boolean hasGold;
    public boolean hasArrow;
    
    public PlayerInfo(Tile location) {
    	this.location = location;
    	this.heading = Direction.NORTH;
    	this.status = Status.ALIVE;
    	this.hasGold = false;
    	this.hasArrow = true;
    }
}

