
public class Engine {
	private Agent agent;
	private int turn;
    private Board board;

    public void turn(Direction heading) {
        turn++;
        agent.heading = heading;
    }

    public boolean move() {
        int toX;
        int toY;

        turn++;

        switch (agent.heading) {
            case Direction.north:
                toX = agent.location.x;
                toY = agent.location.y + 1;
                break;
            case Direction.south:
                toX = agent.location.x;
                toY = agent.location.y - 1;
                break;
            case Direction.east:
                toX = agent.location.x + 1;
                toY = agent.location.y;
                break;
            case Direction.west:
                toX = agent.location.x - 1;
                toY = agent.location.y;
                break;
        }

        if (!board.isValid(toX, toY)) {
            return false;
        }

        agent.location = board.tiles[toX][toY];
        if (agent.location.hasWumpus || agent.location.hasPit) {
            agent.alive = false;
            endGame();
        }

        return true;
    }

    public void grab() {
        turn++;
        if (agent.location.hasGold) {
            agent.gold = true;
            location.hasGold = false;
        }
    }

    public void escape() {
        turn++;
        if (agent.location.x == 0 && agent.location.y == 0) {
            endGame();
        }
    }

    /* returns true if the wumpus died, false otherwise */
    public boolean shoot() {
        turn++;
        if (!agent.hasArrow) {
            return false;
        }
        agent.hasArrow = false;
        x = agent.location.x;
        y = agent.location.y;
        switch (agent.heading) {
            case Direction.north:
                for (; y < board.width; y++) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
            case Direction.south:
                for (; y >= 0; y--) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
            case Direction.east:
                for (; x < board.width; x++) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
            case Direction.west:
                for (; x >= 0; x--) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    /* TODO: add sensory methods */

    public Direction getHeading() {
        return agent.heading;
    }

}
