package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Sword extends Entity {

	private int attacktimes;
	private Dungeon dungeon;

    public Sword(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.attacktimes = 5;
		this.dungeon = dungeon;
    }
	
	@Override
	public int getUsedtimes() {
		return attacktimes;
	}

	@Override
	public void setUsedtimes(int attacktimes) {
		this.attacktimes = attacktimes;
    }
    
    @Override
	public void update(Player player) {
		int playerx = player.getX();
        int playery = player.getY();
        if ( (playerx == this.getX()) && (playery == this.getY())  ) {
			if(player.getBag().getSword() != null) {

			} else {
				player.getBag().setSword(this);
				player.makeArmed();
                dungeon.getEntity().remove(this);
			}
		}
    }
}