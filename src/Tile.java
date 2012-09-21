
public class Tile {
    public int x;
    public int y;
    public boolean hasWumpus;
    public boolean hasGold;
    public boolean hasPit;

    public Tile(int x, int y, boolean w, boolean g, boolean p){
        this.x = x;
        this.y = y;
        hasWumpus = w;
        hasGold = g;
        hasPit = p;
    }

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        hasWumpus = false;
        hasGold = false;
        hasPit = false;
    }
}

