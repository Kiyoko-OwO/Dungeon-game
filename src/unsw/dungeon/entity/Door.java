package unsw.dungeon.entity;

import unsw.dungeon.Dungeon;

public class Door extends Entity {
    private int status;
    private int id;
    private Dungeon dungeon;
    public Door(Dungeon dungeon, int x, int y,  int id){
        super(x, y);
        this.dungeon = dungeon;
        this.status = 1; // 0 = open or 1 = close
        this.id = id; // distinct different door, // 0 = yellow, 1 = red, 2 = blue
    }

 
    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int i){
        this.status = i;
    }

    @Override
	public void update(Player player) {
		int playerx = player.getX();
		int playery = player.getY();
		if ( (playerx == this.getX()) && (playery == this.getY())  ) {
			if(player.getBag().getKey() != null) {
                Key key = player.getBag().getKey();
                if(key.getId() == this.getId()){
                    player.getBag().setKey(null);
                    this.setStatus(0);
                }
				
				
			}
		}
	}
    
}