package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class BlackHole extends Entity {


/*
 * black hole is an entity which whatever entity steps in it gets removed
 * this includes bomb, player and enemy
 */
    private Dungeon dungeon;

	public BlackHole(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;		
	}
	
	
	@Override
	//whenever a change in dungeon happens it should call this
	public void update (Player player) {
		if (player.getX() == this.getX() && player.getY() == this.getY()) {
			player.makeDead();
		}
		for (Entity e: player.getEntityList()) {
			if(e.getX() == this.getX() && e.getY() == this.getY()) {
				if(e instanceof BlackHole) {
					break;
				}else {
					this.dungeon.getEntity().remove(e);
				}
				
			}
			
			
		}
	}

}
