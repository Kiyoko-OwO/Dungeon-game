package unsw.dungeon.attackmode;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Bag;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Player;
import unsw.dungeon.interact.Attack;

public class Invicible implements Attack {
	private Player player;
	private Dungeon presentDungeon;
	private List<Entity> attackedEntities;
	private Bag playerbag;
	private int playerx;
    private int playery;
 
	
	public Invicible(Player player) {
		this.player = player;
		this.presentDungeon = player.getDungeon();
		this.attackedEntities = this.presentDungeon.getEntity();
		this.playerbag = player.getBag();
		this.playerx = player.getX();
		this.playery = player.getY();
        playerbag.setInviciblepotion(5);
	}
	
	@Override
	public void attack(List<Entity> entitylist, int playerx, int playery) {
		for(Entity i : entitylist) {
			int enemyX = i.getX();
			int enemyY = i.getY();
			if ((i instanceof Enemy) &&  (((playerx == enemyX + 1) && (playery == enemyY)) ||
				((playerx == enemyX - 1) && (playery == enemyY)) ||	
				((playerx == enemyX) && (playery == enemyY + 1)) ||
				((playerx == enemyX) && (playery == enemyY - 1)) ||
				((playerx == enemyX) && (playery == enemyY)))) {
				i.setNode(null);
				i.x().set(0);
				i.y().set(0);
				player.getDungeon().removeEntity(i);
    			break;
			}
		}
	}
	
	@Override
	public Attack becomeArmed(Player player) {
		return new Armed(player);
	}

	@Override
	public Attack becomeUnarmed(Player player) {
		return new Unarmed(player);
	}

	@Override
	public Attack becomeInvicible(Player player) {
		throw new UnsupportedOperationException("Invicible can't become invicible again");
	}
}	
