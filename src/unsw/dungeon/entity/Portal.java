package unsw.dungeon.entity;

public class Portal extends Entity{
	
	public int status;
	public int id;
	public Portal(int x, int y, int id) {
        super(x, y);
		this.status = 0;
		this.id = id;
    }
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return this.id;
	}

}