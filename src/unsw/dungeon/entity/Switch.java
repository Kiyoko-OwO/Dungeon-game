package unsw.dungeon.entity;

public class Switch extends Entity{
	private int covered;
	public Switch(int x, int y) {
        super(x, y);
        this.covered = 0;
	}


	public int getCovered() {
		return this.covered;
	}


	public void setCovered(int covered) {
		this.covered = covered;
	}
	@Override
	public void update(Player player) {
		this.setCovered(0);
		for (Entity e : player.getEntityList()) {
			if( (e instanceof Boulder) && (e.getX() == this.getX()) && (e.getY() == this.getY())) {
				this.setCovered(1);
			}
		}
	}
	
	
}