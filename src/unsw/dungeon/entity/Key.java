package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Key extends Entity {
    private int id;
    private Dungeon dungeon;
	public Key(Dungeon dungeon, int x, int y, int id) {
		super(x, y);
        this.id = id; // 0 = yellow, 1 = red, 2 = blue
        this.dungeon = dungeon;
    }

    public int getId(){
        return id;
    }

    @Override
    public void update(Player player){
        
        int playerx = player.getX();
        int playery = player.getY();
        if ( (playerx == this.getX()) && (playery == this.getY())  ) {
			if(player.getBag().getKey() != null) {

			} else {
                player.getBag().setKey(this);
                dungeon.getEntity().remove(this);
			}
		}
    }
}