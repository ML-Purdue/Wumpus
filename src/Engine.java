
public class Engine {

	private Agent agent;
	private int turn;
    private Board game;

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

        agent.location = game.tiles[toX][toY];
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

    public boolean shoot() {
        turn++;
        agent.hasArrow = false;
        switch (agent.heading) {
            case Direction.north:
                for (y = agent.location.y; y < game.width; y++) {
                    if (board.tiles[x][y].hasWumpus) {
                    }
                }

                break;
            case Direction.south:
                break;
            case Direction.east:
                break;
            case Direction.west:
                break;
        }

        return false;
    }


    public Tile getCurrentTile() {
        return agent.location;
    }

    public Direction getHeading() {
        return agent.heading;
    }


}
