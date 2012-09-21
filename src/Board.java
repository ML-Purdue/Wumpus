import java.util.Random;

public class Board {
    int width;
    Tile[][] tiles;

    public Board(int w, int num_pits){
        Random rand = new Random();

        width = w;
        tiles = new Tile[w][w];
        for(int i = 0; i < w; i++){
            for(int j = 0; j < w; j++){
                tiles[i][j] = new Tile(j, i);
            }
        }

        //Set the position of the wumpus
        for(int i = 0; i < 1; i++){
            int w_x = rand.nextInt(width);
            int w_y = rand.nextInt(width);
            if(w_x != 0 && w_y != 0)//Don't spawn the Wumpus on the agent
                tiles[w_y][w_x].hasWumpus = true;
            else
                i--;
        }

        //Set the position of the gold
        for(int i = 0; i < 1; i++){
            int g_x = rand.nextInt(width);
            int g_y = rand.nextInt(width);
            if(g_x != 0 && g_y != 0)//Don't spawn the gold on the agent
                tiles[g_y][g_x].hasGold = true;
            else
                i--;
        }

        //Set the position of the pits
        for(int i = 0; i < num_pits; i++){
            int p_x = rand.nextInt(width);
            int p_y = rand.nextInt(width);
            //Make sure pits aren't spawned on anything else
            if(p_x != 0 && p_y != 0 && !tiles[p_y][p_x].hasWumpus && !tiles[p_y][p_x].hasGold && !tiles[p_y][p_x].hasPit)
                tiles[p_y][p_x].hasPit = true;
            else
                i--;
        }
    }

    //Query the stinkyness of a tile
    public boolean isStinky(int x, int y){
        for(int i = x-1; i < x+1; i++)
            for(int j = y-1; j < y+1; j++)
                if(isValid(j, i) && tiles[i][j].hasWumpus)
                    return true;
        return false;
    }

    //Query the windyness of a tile
    public boolean isWindy(int x, int y){
        for(int i = y-1; i < y+1; i++)
            for(int j = x-1; j < x+1; j++)
                if(isValid(j, i) && tiles[i][j].hasPit)
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

