package agent;

import environment.Direction;

public class Agent {
    public Direction heading;
    public boolean alive;
    public boolean hasGold;
    public boolean hasArrow;

    public Agent(){
        heading = Direction.north;
        alive = true;
        hasGold = false;
        hasArrow = true;
    }
}

