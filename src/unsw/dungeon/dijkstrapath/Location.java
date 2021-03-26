package unsw.dungeon.dijkstrapath;
/**
 * A class to help the dijikstra algorithm
 */
public class Location {
    private int x;
    private int y;
    private int length;
    private Location prev;

    public Location(int x, int y, int length, Location prev) {
        this.x = x;
        this.y = y;
        // length = -1 means that never be arrived, 
        // length = -2 means that this classis used to get Furthest Point
        this.length = length; 
        this.prev = prev;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getleng() {
        return this.length;
    }

    public Location getprev() {
        return this.prev;
    }

    public boolean checkSame(int newx, int newy) {
        if (newx == this.x && newy == this.y)   return true;
        return false;
    }

    public void givePrev(int newLen, Location newPrev) {
        this.length = newLen;
        this.prev = newPrev;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}