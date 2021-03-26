package unsw.dungeon.entity;

import java.time.LocalTime;

public class Bag {
    private Key key;
    private Sword sword;
    private int inviciblepotion;
    private LocalTime endInvicible;
    private Bomb bomb;
    public Bag() {
        this.key =null; 
        this.sword = null;
        this.inviciblepotion = 0;
        this.endInvicible = null;
        this.bomb = null;
    }
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key haskey) {
		this.key = haskey;
    }
    
    public int getKeyId(){
        return key.getId();
    }

    public Sword getSword(){
      return sword;
    }

    public Bomb getBomb() {
      return bomb;
    }
  
    public void setBomb(Bomb bomb) {
      this.bomb = bomb;
    }

    public void setSword(Sword sword){
      this.sword = sword;
    }
    public int getSwordTime(){
      if(this.sword == null){
        return 0;
      }else{
        return sword.getUsedtimes();
      }
      
    }
    public void setSwordTime(int swordattacktimes) {
      sword.setUsedtimes(swordattacktimes);
    }
    public int getInviciblepotion() {
		  return inviciblepotion;
    }
    
    public LocalTime getEnd() {
      return this.endInvicible;
    }

	public void setInviciblepotion(int inviciblepotion) {
    this.inviciblepotion = inviciblepotion;
    this.endInvicible = LocalTime.now().plusSeconds(inviciblepotion);
	}
}