package unsw.dungeon.entity;


import unsw.dungeon.Dungeon;

public class Potion extends Entity{
	private int valueTime;
	private Dungeon dungeon;

	public Potion(Dungeon dungeon, int x, int y) {
		super(x, y);		// value time of potion is 15s.
		this.dungeon = dungeon;
		this.valueTime = 5;
    }

	@Override
	public int getUsedtimes() {
		return valueTime;
	}
	
	@Override
	public void setUsedtimes(int valueTime) {
		this.valueTime = valueTime;
	}
	
	@Override
	public void update(Player player) {
		int playerx = player.getX();
		int playery = player.getY();
		if ( (playerx == this.getX()) && (playery == this.getY())  ) {
			if ( player.getBag().getInviciblepotion() > 0) {
			}
			else {
				player.getBag().setInviciblepotion(5);
				player.makeInvicible();
				dungeon.removeEntity(this);
			}
		}
	}
}