package unsw.dungeon.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity implements EntityObserver{

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private int changed;
    private int usedtimes; // sword, key, potion have limit use time
    private String type;
    private int covered;
    private int bombstatus;
    private ImageView imageView;
    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.changed = 0;
        this.usedtimes = 0;
        this.type = null;
        this.covered = 0;
        this.bombstatus = 0;
        this.imageView = null;
    }

    public int getBombStatus() {
    	return this.bombstatus;
    }
    
    public void setBombStatus(int i) {
    	this.bombstatus = i;
    }
    
    public IntegerProperty x() {
        return this.x;
    }

    public IntegerProperty y() {
        return this.y;
    }

    public int getY() {
        return this.y().get();
    }

    public int getX() {
        return this.x().get();
    }

    public void setX(IntegerProperty x) {
        this.x = x;
    }

    public void setY(IntegerProperty y) {
        this.y = y;
    }
    
    public int getCovered() {
		return covered;
	}
        
	public void setCovered(int covered) {
		this.covered = covered;
	}
    public String getType(){
        return type;
    }

    public int getChanged() {
        return changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }
    
    public void setNode(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getNode() {
        return imageView;
    }
    @Override
    public void update(Player player){

    }
	public void attack(Player player, int x, int y) {
		
	}
	public int getUsedtimes() {
		return usedtimes;
	}

	public void setUsedtimes(int usedtimes) {
		this.usedtimes = usedtimes;
	}

}
