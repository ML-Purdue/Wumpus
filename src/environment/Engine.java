package environment;


public class Engine {
	private Agent agent;
    private Board board;
	private int turn;
    private int score;
    private boolean inProgress;
    
    public Engine (int width) {
    	double pit_constant = Math.random() * .5 + .5;
    	int num_pits = (int) (width * pit_constant);
    	this.board = new Board(width,num_pits);
    	this.agent = new Agent(board.tiles[0][0]);
    	this.turn = 0;
    	this.score = 0;
    	this.inProgress = true;
    }
    
    private void endGame() {
    	this.inProgress = false;
    	score = 0;
    	if (agent.alive)
    		if (agent.hasGold)
    			score += 1000;		// +1000 for gold
    	else
    		score -= 1000;			// -1000 for death
    	if (!agent.hasArrow)
    		score -= 10;			// -10 for cost of arrow
    	score -= turn;				// -1 per turn
    }

    /* Player Actions */
    public void turn(Direction heading) {
        turn++;
        agent.heading = heading;
    }

    public boolean move() {
        int toX = 0;
        int toY = 0;

        turn++;

        switch (agent.heading) {
            case north:
                toX = agent.location.x;
                toY = agent.location.y + 1;
                break;
            case south:
                toX = agent.location.x;
                toY = agent.location.y - 1;
                break;
            case east:
                toX = agent.location.x + 1;
                toY = agent.location.y;
                break;
            case west:
                toX = agent.location.x - 1;
                toY = agent.location.y;
                break;
        }

        if (!board.isValid(toX, toY)) {
            return false;
        }

        agent.location = board.tiles[toX][toY];
        if (board.tiles[agent.location.x][agent.location.x].hasWumpus ||
        		board.tiles[agent.location.x][agent.location.x].hasPit) {
            agent.alive = false;
            endGame();
        }

        return true;
    }

    public void grab() {
        turn++;
        if (agent.location.hasGold) {
            agent.hasGold = true;
            agent.location.hasGold = false;
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
        int x = agent.location.x;
        int y = agent.location.y;

        switch (agent.heading) {
            case north:
                for (; y < board.width; y++) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
            case south:
                for (; y >= 0; y--) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
            case east:
                for (; x < board.width; x++) {
                    if (board.tiles[x][y].hasWumpus) {
                        board.tiles[x][y].hasWumpus = false;
                        return true;
                    }
                }
                break;
            case west:
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
    
    public boolean hasArrow() {
    	return agent.hasArrow;
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

        for(int y = 0; y < board.width; y++){
            for(int x = 0; x < board.width; x++){
                buf[x] = ' ';
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
                    buf[x] = 'W';
                if(board.tiles[y][x].hasPit)
                    buf[x] = 'P';
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
