/**
 *
 */
package unsw.dungeon;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import unsw.dungeon.entity.*;
import unsw.dungeon.goal.*;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {
    private String file;
    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Goal goal;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new CopyOnWriteArrayList<>();
        this.player = null;
        this.goal = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity){
        entities.remove(entity);
    }

    public List<Entity> getEntity(){
    	return this.entities;
    }


    public void setGoal(Goal goal) {
        if (this.goal != null) {
            return;
        }
        this.goal = goal;
        this.player.updateGoal();
    }

    public Goal getGoal() {
        return this.goal;
    }
    
	public Iterator<Entity> iterator() {
		return null;
    }
    
    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
