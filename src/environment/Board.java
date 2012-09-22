package environment;

import java.util.Random;

public class Board {
    public int width;
    protected Tile[][] tiles;  // tiles[y][x]

    public Board(int w, int num_pits){
        Random rand = new Random();

        width = w;
        tiles = new Tile[w][w];
        for(int y = 0; y < w; y++){
            for(int x = 0; x < w; x++){
                tiles[y][x] = new Tile(x, y);
            }
        }
        
        /* Note: probably wouldn't matter, but might help to replace with a knuth
         * shuffle of valid tiles?
         */

        //Set the position of the wumpus
        for(int i = 0; i < 1; i++){
            int w_x = rand.nextInt(width);
            int w_y = rand.nextInt(width);
            if(w_x != 0 || w_y != 0)//Don't spawn the Wumpus on the agent
                tiles[w_y][w_x].hasWumpus = true;
            else
                i--;
        }

        //Set the position of the gold
        for(int i = 0; i < 1; i++){
            int g_x = rand.nextInt(width);
            int g_y = rand.nextInt(width);
            if(g_x != 0 || g_y != 0)//Don't spawn the gold on the agent
                tiles[g_y][g_x].hasGold = true;
            else
                i--;
        }

        //Set the position of the pits
        for(int i = 0; i < num_pits; i++){
            int p_x = rand.nextInt(width);
            int p_y = rand.nextInt(width);
            //Make sure pits aren't spawned on anything else
            if(!(p_x == 0 && p_y == 0) && !tiles[p_y][p_x].hasWumpus && !tiles[p_y][p_x].hasGold && !tiles[p_y][p_x].hasPit)
                tiles[p_y][p_x].hasPit = true;
            else
                i--;
        }
    }

    //Query the stinkyness of a tile
    public boolean isStinky(int x, int y){
    	if (isValid(x-1, y) && tiles[y][x-1].hasWumpus ||
    			isValid(x+1, y) && tiles[y][x+1].hasWumpus ||
    			isValid(x, y-1) && tiles[y-1][x].hasWumpus ||
    			isValid(x, y+1) && tiles[y+1][x].hasWumpus)
    		return true;
    	return false;
    }

    //Query the windyness of a tile
    public boolean isWindy(int x, int y){
    	if (isValid(x-1, y) && tiles[y][x-1].hasPit ||
    			isValid(x+1, y) && tiles[y][x+1].hasPit ||
    			isValid(x, y-1) && tiles[y-1][x].hasPit ||
    			isValid(x, y+1) && tiles[y+1][x].hasPit)
    		return true;
        return false;
    }

    //Query the goldyness of a tile
    public boolean hasGold(int x, int y){
        return tiles[y][x].hasGold;
    }

    public boolean isValid(int x, int y){
        return (x < width && x >= 0) && (y < width && y >= 0);
    }
}

