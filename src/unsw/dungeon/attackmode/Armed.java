package unsw.dungeon.attackmode;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Bag;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Player;
import unsw.dungeon.interact.Attack;

public class Armed implements Attack {
	Player player;
	private Dungeon presentDungeon;
	private List<Entity> attackedEntities;
	private Bag playerbag;

	
	public Armed(Player player) {
		this.player = player;
		this.presentDungeon = player.getDungeon();
		this.attackedEntities = this.presentDungeon.getEntity();
        this.playerbag = player.getBag();
        playerbag.setSwordTime(playerbag.getSwordTime());
	}
	
	@Override
	public void attack(List<Entity> entitylist, int playerx, int playery) {
		for(Entity i : entitylist) {
			if (i == null)	break;
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
				playerbag.setSwordTime(playerbag.getSwordTime() - 1);
				player.getEntityList().remove(i);
    			if (playerbag.getSwordTime() == 0) {
    				player.makeUnarmed();
    			}
			}
		}
	}
	
	@Override
	public Attack becomeArmed(Player player) {
		throw new UnsupportedOperationException("Armed can't become new armed");
	}

	@Override
	public Attack becomeUnarmed(Player player) {
		return new Unarmed(player);
	}

	@Override
	public Attack becomeInvicible(Player player) {
		return new Invicible(player);
	}
	
	
}
