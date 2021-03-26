package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Treasure extends Entity{
    private Dungeon dungeon;

    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    @Override
    public void update(Player player) {
        int playerx = player.getX();
        int playery = player.getY();
        if ( (playerx == this.getX()) && (playery == this.getY()) ) {
            dungeon.getEntity().remove(this);
        }
    }
}