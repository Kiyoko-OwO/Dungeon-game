package unsw.dungeon.entity;

public class Exit extends Entity{
	public Exit(int x, int y) {
        super(x, y);
    }

	public void update(Player player) {
		if ((player.getX() == this.getX()) &&  (player.getY() == this.getY()) ) {
			player.setExit(1);
		} else {
			player.setExit(0);
		}
	}

}