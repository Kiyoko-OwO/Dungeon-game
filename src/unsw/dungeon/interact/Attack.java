package unsw.dungeon.interact;

import java.util.List;

import unsw.dungeon.entity.Entity;
import unsw.dungeon.entity.Player;

public interface Attack {
	public void attack(List<Entity> entitylist, int playerxx, int playeryy);
	public Attack becomeArmed(Player player);
	public Attack becomeUnarmed(Player player);
	public Attack becomeInvicible(Player player);
}