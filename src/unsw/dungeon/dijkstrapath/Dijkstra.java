package unsw.dungeon.dijkstrapath;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;
import java.util.*;
import java.util.concurrent.Future;

public class Dijkstra {
    private Queue<Location> locations;
    private ArrayList<Location> path;
    private ArrayList<Location> outs;
    private Dungeon dungeon;

    public Dijkstra(Dungeon dungeon) {
        this.locations = new LinkedList<Location>();
        this.path = new ArrayList<Location>();
        this.outs = new ArrayList<Location>();
        this.dungeon = dungeon;
    }

    public ArrayList<Location> getPath() {
        return this.path;
    }

    public ArrayList<Location> getshortestPath(int startX, int startY, int endX, int endY) {
        int minDist = 0;
        Location closest = null;

        checkAll(startX, startY, false);
        boolean found = false;
        boolean first = true;
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                int distance = Math.abs(x - endX) + Math.abs(y - endY);
                if (first && whethercouldmove(x, y, false)) {
                    minDist = distance + 1;
                    first = false;
                }
                if (whethercouldmove(x, y, false) && distance < minDist) {
                    Location curr = checkCanbeReached(x, y);
                    if (curr != null) {
                        minDist = distance;
                        closest = curr;
                        if (curr.checkSame(endX, endY)) {
                            found = true;
                            break;
                        }
                    }
                }
            }
            if (found)  break;
        }
        
        createPath(closest);
        return this.path;
    }

    private void createPath(Location lo) {
        if (lo == null) return;
        if (lo.getprev() == null) {
            return;
        }
        if (lo.getprev().equals(lo))    return;
        
        this.path.add(lo);
        createPath(lo.getprev());
    }

    private void addNewAndCheckDest (int newX, int newY, int len, Location out) {
        if (newX > this.dungeon.getWidth() || newY > this.dungeon.getHeight() ||
            newX < 0 || newY < 0)   return;
        
        
        for (Location curr : this.locations) {
            if (curr.checkSame(newX, newY)) {
                if (curr.getleng() > len) {
                    curr.givePrev(len, out);
                }
                return;
            }
        }

        for (Location curr : this.outs) {
            if (curr.checkSame(newX, newY)) {
                return;
            }
        }

        Location one = new Location(newX, newY, len, out);
        locations.add(one);
        return;
    }

    // furthes to check if the path is shortest or furthest
    private boolean whethercouldmove(int newX, int newY, boolean furthest) {
        for (Entity i : this.dungeon.getEntity()) {
            if (i == null)  return true;
            if (newX == i.getX() && newY == i.getY()) {
                if (i instanceof Wall || i instanceof Boulder) {
                    return false;
                }
                if (i instanceof Door) {
                    Door door = (Door) i;
                    if (door.getStatus() == 1)     return false;
                }
            }
            if (i instanceof Player && furthest) {
                if ((newX <= i.getX() + 3 && newX >= i.getX() - 3) && 
                    (newY <= i.getY() + 3 && newY >= i.getY() - 3)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkAll(int x, int y, boolean furthest) {
        this.outs = new ArrayList<Location>();
        Location start = new Location(x, y, 0, null);
        start.givePrev(0, start);

        this.locations.add(start);
        while (! locations.isEmpty()) {
            Location out = locations.remove();
            this.outs.add(out);
            int len = out.getleng() + 1;
            int outX = out.getX();
            int outY = out.getY();

            if (whethercouldmove(outX + 1, outY, furthest)) {
                addNewAndCheckDest(outX + 1, outY, len, out);
            }

            if (whethercouldmove(outX - 1, outY, furthest)) {
                addNewAndCheckDest(outX - 1, outY, len, out);
            }

            if (whethercouldmove(outX, outY + 1, furthest)) {
                addNewAndCheckDest(outX, outY + 1, len, out);
            }

            if (whethercouldmove(outX, outY - 1, furthest)) {
                addNewAndCheckDest(outX, outY - 1, len, out);
            }
            
        }
    }

    private Location checkCanbeReached(int x, int y) {
        for (Location curr : this.outs) {
            if (curr.checkSame(x, y)) {
                return curr;
            }
        }
        return null;
    }
    
    public ArrayList<Location> getFurthestPath(int playerX, int playerY, int enemyX, int enemyY) {
        int maxDist = 0;
        Location furthest = null;

        checkAll(enemyX, enemyY, true);
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                int distance = Math.abs(x - playerX) + Math.abs(y - playerY);
                if (whethercouldmove(x, y, true) && distance > maxDist) {
                    Location curr = checkCanbeReached(x, y);
                    if (curr != null) {
                        maxDist = distance;
                        furthest = curr;
                    }
                }
            }
        }
        createPath(furthest);
        return this.path;
    }
}
