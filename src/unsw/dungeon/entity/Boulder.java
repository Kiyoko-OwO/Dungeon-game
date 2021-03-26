package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Boulder extends Entity {
    private Dungeon dungeon;
    public Boulder(Dungeon dungeon, int x, int y){
        super(x, y);
        this.dungeon = dungeon;
    }

    @Override
    public void update(Player player){
        int playX = player.getX();
        int playY = player.getY();

        if((this.getX() == playX) && (playY == this.getY())){
            switch(player.getLastMove()) {
				case "Up":
					this. y().set(getY()-1);
					break;
				case "Down":
					this. y().set(getY()+1);
					break;
				case "Left":
					this. x().set(getX()-1);
					break;
				case "Right":
					this. x().set(getX()+1);
					break;
				default:
					break;
			}
		}
		
		for (Entity e : this.dungeon.getEntity()) {
			if (e instanceof Switch) {
				e.update(player);
			}
		}
    }


}