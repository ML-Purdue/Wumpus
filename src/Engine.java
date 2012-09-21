
public class Engine {

	private Agent agent;
	private int turn;
    private Board game;

    public void turn(Direction heading) {
        agent.heading = heading;
        turn++;
    }

    public void grab() {
        if (agent.location.hasGold) {
            agent.gold = true;
            location.hasGold = false;
        }
        turn++;
    }

    public Tile getCurrentTile() {
        return agent.location;
    }

    public Direction getHeading() {
        return agent.heading;
    }


}
