package environment;


public class Engine {
	private PlayerInfo agent;
    private Board board;
	private int turn;
    private int score;
    private boolean inProgress;
    private boolean wumpusAlive;
    
    public Engine (int width) {
    	double pit_constant = Math.random() * .5 + .5;
    	int num_pits = (int) (width * pit_constant);
    	this.board = new Board(width,num_pits);
    	this.agent = new PlayerInfo(board.tiles[0][0]);
    	this.turn = 0;
    	this.score = 0;
    	this.inProgress = true;
        this.wumpusAlive = true;
    }
    
    private void endGame() {
    	this.inProgress = false;
    	score = 0;
    	if (agent.status == Status.ESCAPED) {
    		if (agent.hasGold) {
    			score += 1000;		// +1000 for gold
    		}
    	} else
    		score -= 1000;			// -1000 for death
    	if (!agent.hasArrow)
    		score -= 10;			// -10 for cost of arrow
        if (!wumpusAlive)
            score += 100;           // +100 for killing the wumpus
    	score -= turn;				// -1 per turn

        for(int i = 0; i < board.width; i++){
            for(int j = 0; j < board.width; j++){
                board.tiles[j][i].agentHasSeen = true;
            }
        }
    }

    /* Player Actions
     * All return true on success, false otherwise */
    public boolean turn(Direction heading) {
        turn++;
        agent.heading = heading;
        return true;
    }

    public boolean forward() {
        int toX = 0;
        int toY = 0;

        turn++;

        switch (agent.heading) {
            case NORTH:
                toX = agent.location.x;
                toY = agent.location.y + 1;
                break;
            case SOUTH:
                toX = agent.location.x;
                toY = agent.location.y - 1;
                break;
            case EAST:
                toX = agent.location.x + 1;
                toY = agent.location.y;
                break;
            case WEST:
                toX = agent.location.x - 1;
                toY = agent.location.y;
                break;
        }

        if (!board.isValid(toX, toY)) {
            return false;
        }

        agent.location = board.tiles[toY][toX];
        if (agent.location.hasWumpus) {
        	agent.status = Status.EATEN;
        	endGame();
        } else if (agent.location.hasPit) {
        	agent.status = Status.FALLEN;
        	endGame();
        }

        board.tiles[toY][toX].agentHasSeen = true;

        return true;
    }

    public boolean grab() {
        turn++;
        if (agent.location.hasGold) {
            agent.hasGold = true;
            agent.location.hasGold = false;
            return true;
        }
        return false;
    }

    public boolean escape() {
        turn++;
        if (agent.location.x == 0 && agent.location.y == 0) {
        	agent.status = Status.ESCAPED;
            endGame();
            return true;
        }
        return false;
    }

    /* returns true if the wumpus died, false otherwise */
    public boolean shoot() {
        turn++;
        if (!agent.hasArrow) {
            return false;
        }
        agent.hasArrow = false;
        int x = agent.location.x;
        int y = agent.location.y;

        switch (agent.heading) {
            case NORTH:
                for (; y < board.width; y++) {
                    if (board.tiles[y][x].hasWumpus) {
                        board.tiles[y][x].hasWumpus = false;
                        wumpusAlive = false;
                        return true;
                    }
                }
                break;
            case SOUTH:
                for (; y >= 0; y--) {
                    if (board.tiles[y][x].hasWumpus) {
                        board.tiles[y][x].hasWumpus = false;
                        wumpusAlive = false;
                        return true;
                    }
                }
                break;
            case EAST:
                for (; x < board.width; x++) {
                    if (board.tiles[y][x].hasWumpus) {
                        board.tiles[y][x].hasWumpus = false;
                        wumpusAlive = false;
                        return true;
                    }
                }
                break;
            case WEST:
                for (; x >= 0; x--) {
                    if (board.tiles[y][x].hasWumpus) {
                        board.tiles[y][x].hasWumpus = false;
                        wumpusAlive = false;
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    /* sensory methods */
    public boolean isStinky() {
    	return board.isStinky(agent.location.x, agent.location.y);
    }
    
    public boolean isWindy() {
    	return board.isWindy(agent.location.x, agent.location.y);
    }
    
    public boolean hasGold() {
    	return board.hasGold(agent.location.x, agent.location.y);
    }
    
    public boolean holdingArrow() {
    	return agent.hasArrow;
    }
    
    public boolean holdingGold() {
    	return agent.hasGold;
    }
    
    public Status getAgentStatus() {
    	return agent.status;
    }

    public Direction getHeading() {
        return agent.heading;
    }
    
    public int getX() {
    	return agent.location.x;
    }
    
    public int getY() {
    	return agent.location.y;
    }
    
    /* Game status */
    public int getScore() {
    	return score;
    }
    
    public boolean inProgress() {
    	return inProgress;
    }
    
    public int getTurn() {
    	return turn;
    }

    public void draw(){
        //buffer each line so that we can overwrite breeze with Wumpus, etc.
        char[] buf = new char[board.width];

        for(int y = board.width-1; y >= 0; y--){
            for(int x = 0; x < board.width; x++){
                buf[x] = '.';
                if(board.tiles[y][x].agentHasSeen){
                    buf[x] = '-';
                    if(board.isStinky(x, y))
                        buf[x] = '^';
                    if(board.isWindy(x, y))
                        if(buf[x] == '^')
                            buf[x] = 'o';
                        else
                            buf[x] = '~';
                    if(board.hasGold(x, y))
                        buf[x] = 'G';
                    if(board.tiles[y][x].hasWumpus)
                        if(wumpusAlive = true)
                            buf[x] = 'W';
                        else
                            buf[x] = 'M';
                    if(board.tiles[y][x].hasPit)
                        buf[x] = 'P';
                    if(x == agent.location.x && y == agent.location.y && agent.status == Status.ALIVE)
                        buf[x] = 'A';
                }
            }
            //print the row
            System.out.println(buf);
        }
    }

    public static void main(String argv[]){
        Engine eng = new Engine(4);
        eng.draw();
    }
}
