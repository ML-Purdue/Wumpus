package environment;

import agent.Agent;

public class Engine {
	private Agent agent;
    private int agent_x;
    private int agent_y;
	private int turn;
    private Board board;

    public Engine(int width) {
        agent = new Agent();
        agent_x = 0;
        agent_y = 0;
        turn = 0;
        board = new Board(width, 3);
    }

    public void turn(Direction heading) {
        turn++;
        agent.heading = heading;
    }

    public boolean move() {
        int toX = -1;
        int toY = -1;

        turn++;

        switch (agent.heading) {
            case north:
                toX = agent_x;
                toY = agent_y + 1;
                break;
            case south:
                toX = agent_x;
                toY = agent_y - 1;
                break;
            case east:
                toX = agent_x + 1;
                toY = agent_y;
                break;
            case west:
                toX = agent_x - 1;
                toY = agent_y;
                break;
        }

        if (!board.isValid(toX, toY)) {
            return false;
        }

        agent_x = toX;
        agent_y = toY;
        if (board.tiles[agent_y][agent_x].hasWumpus || board.tiles[agent_y][agent_x].hasPit) {
            agent.alive = false;
            endGame();
        }

        return true;
    }

    public void grab() {
        turn++;
        if (board.tiles[agent_y][agent_x].hasGold) {
            agent.hasGold = true;
            board.tiles[agent_y][agent_x].hasGold = false;
        }
    }

    public void escape() {
        turn++;
        if (agent_x == 0 && agent_y == 0) {
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
        int x = agent_x;
        int y = agent_y;
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

    /* TODO: add sensory methods */

    public Direction getHeading() {
        return agent.heading;
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

    public void endGame(){
        System.out.println("You have won the game!");
        System.exit(0);
    }

    public static void main(String argv[]){
        Engine eng = new Engine(4);
        eng.draw();
    }
}
