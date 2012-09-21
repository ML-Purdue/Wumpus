import java.Math;

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
        int w_x = (Math.random() * width);
        int w_y = (Math.random() * width);
        tile[w_y][w_x].hasWumpus = true;

        //Set the position of the gold
        int g_x = (Math.random() * width);
        int g_y = (Math.random() * width);
        tile[g_y][g_x].hasGold = true;

        //Set the position of the pits
        for(int i = 0; i < num_pits; i++){
            int p_x = (Math.random() * width);
            int p_y = (Math.random() * width);
            if(!tile[p_y][p_x].hasWumpus && !tile[p_y][p_x].hasGold)
                tile[p_y][p_x].hasPit = true;
            else
                i--;
        }
    }

    //Query the stinkyness of a tile
    public boolean isStinky(int x, int y){
        for(int i = x-1; i < x+1; i++)
            for(int j = y-1; j < y+1; j++)
                if((i < width && i >= 0) && (j < width && j >= 0) && tiles[i][j].hasWumpus)
                    return true;
        return false;
    }

    //Query the windyness of a tile
    public boolean isWindy(int x, int y){
        for(int i = y-1; i < y+1; i++)
            for(int j = x-1; j < x+1; j++)
                if((i < width && i >= 0) && (j < width && j >= 0) && tiles[i][j].hasPit)
                    return true;
        return false;
    }

    //Query the goldyness of a tile
    public boolean hasGold(int x, int y){
        return tiles[y][x].hasGold;
    }
}

