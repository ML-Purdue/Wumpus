
public class Board {
    int width;
    Tile[][] tiles;

    public Board(int w, int num_pits){
        width = w;
        tiles = new Tile[w][w];
        for(int i = 0; i < w; i++){
            for(int j = 0; j < w; j++){
                tiles[i][j] = new Tile(j, i);
            }
        }

        //Set the position of the wumpus
        int w_x = (int)(Math.random() * width);
        int w_y = (int)(Math.random() * width);
        tiles[w_y][w_x].hasWumpus = true;

        //Set the position of the gold
        int g_x = (int)(Math.random() * width);
        int g_y = (int)(Math.random() * width);
        tiles[g_y][g_x].hasGold = true;

        //Set the position of the pits
        for(int i = 0; i < num_pits; i++){
            int p_x = (int)(Math.random() * width);
            int p_y = (int)(Math.random() * width);
            if(!tiles[p_y][p_x].hasWumpus && !tiles[p_y][p_x].hasGold)
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

