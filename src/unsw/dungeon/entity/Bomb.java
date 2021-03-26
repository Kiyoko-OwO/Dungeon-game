package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;
import unsw.dungeon.attackmode.Invicible;

public class Bomb extends Entity {
    private int explosiontype;
    private Dungeon dungeon;
    private int x;
	private int y; 
    public Bomb(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.dungeon = dungeon;
    }
    public int getExplosiontype() {
		return explosiontype;
	}
	
	public void setExplosiontype(int explosiontype) {
		this.explosiontype = explosiontype;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public void update(Player player) {
		int playerx = player.getX();
		int playery = player.getY();
		if ( (playerx == this.getX()) && (playery == this.getY())  ) {
			if ( player.getBag().getBomb() != null) {
            }else if(this.getBombStatus() == 1){
			}else {
				player.getBag().setBomb(this);
				this.dungeon.getEntity().remove(this);
			}
		}
	}
	
	@Override
	public void attack(Player player, int x, int y) {
		for(Entity i: player.getEntityList()) {
			if( (i.getX()== x-1) && (i.getY() == y) ) {
				if((i instanceof Enemy )|| (i instanceof Boulder)) {
					this.dungeon.getEntity().remove(i);
				}
			}
			else if( (i.getX()== x+1) && (i.getY() == y) ) {
				if( (i instanceof Enemy) || (i instanceof Boulder)) {
					this.dungeon.getEntity().remove(i);
				}
			}
			else if( (i.getX()== x) && (i.getY() == y-1) ) {
				if( (i instanceof Enemy)|| (i instanceof Boulder)){
					this.dungeon.getEntity().remove(i);
				}
			}
			else if( (i.getX()== x) && (i.getY() == y+1) ) {
				if( (i instanceof Enemy)|| (i instanceof Boulder)) {
					this.dungeon.getEntity().remove(i);
				}
			}
			else if( (i.getX()== x) && (i.getY() == y) ) {
				if( (i instanceof Enemy)|| (i instanceof Boulder)) {
					this.dungeon.getEntity().remove(i);
				}
			}
		}
		if( (player.getX()== x-1) && (player.getY() == y) ) {
			if(!(player.getAttackmode() instanceof Invicible)) {
				player.makeDead();
			}
		}
		else if( (player.getX()== x+1) && (player.getY() == y) ) {
			if(!(player.getAttackmode() instanceof Invicible)) {
				player.makeDead();
			}
		}
		else if( (player.getX()== x) && (player.getY() == y-1) ) {
			if(!(player.getAttackmode() instanceof Invicible)) {
				player.makeDead();
			}
		}
		else if( (player.getX()== x) && (player.getY() == y+1) ) {
			if(!(player.getAttackmode() instanceof Invicible)) {
				player.makeDead();
			}
		}
		else if( (player.getX()== x) && (player.getY() == y) ) {
			if(!(player.getAttackmode() instanceof Invicible)) {
				player.makeDead();
			}
		}
		
		for(Entity e: player.getEntityList()) {
			if((e instanceof Switch) && (e.getCovered()==1)) {
				int on = 0;
				for(Entity k:player.getEntityList()) {
					if(  (k instanceof Boulder) && (k.getX()==e.getX()) && (k.getY()==e.getY())) {
						on = 1;
					}
				}
				if(on==0) {
					e.setCovered(0);
				}
			}
    
        }
    }
}