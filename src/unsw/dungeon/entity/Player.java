package unsw.dungeon.entity;

import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import unsw.dungeon.Dungeon;
import unsw.dungeon.attackmode.Invicible;
import unsw.dungeon.attackmode.Unarmed;
import unsw.dungeon.interact.*;
import unsw.dungeon.goal.*;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */

public class Player extends Entity implements Moving, Subject {
    private boolean status;
    private String lastMove;
    private Dungeon dungeon;
    private Bag bag;
    private String type;
    private List<Entity> entityList;
    private Attack attackmode;
    private int exit;
    //private int result; // player end game or player still in game
    private Goal goal;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.type = null;
        this.status = true;
        this.lastMove = "stop";
        entityList = dungeon.getEntity();
        this.bag = new Bag();
        this.attackmode = new Unarmed(this);
        //this.result = 0;    // 0 = in game, 1 = end game
        this.goal = dungeon.getGoal();
        this.exit = 0; //1: exist, 0: not
        //this.result = 0; // 0 = in game, 1 = end game
    }

    public void updateGoal() {
        this.goal = this.dungeon.getGoal();
    }
    
    public boolean getStatus(){
        return this.status;
    }

    public int getExit() {
		return exit;
	}

	public void setExit(int exit) {
        this.exit = exit;
        if (this.exit == 1) {
            if (! this.dungeon.getGoal().checkGoal()) {
                System.out.println("You cannot exit, go back and try to finish all goals!");
            } else {
                System.out.println("Game End! You Win!");
            }
        }
	}

    public String getLastMove() {
		return lastMove;
    }
    
	public void setLastMove(String l) {
		this.lastMove = l;
    }


    public Dungeon getDungeon() {
    	return this.dungeon;
    }


    public List<Entity> getEntityList(){
        return entityList;
    }
    
    public String getType(){
        return type;
    }
    public Bag getBag(){
        return bag;
    }

    public void setBag(Bag bag){
        this.bag = bag;
    }
    //--------------------------Observer Pattern--------------------------------------
	@Override
	public void notifyPickEntityObservers() {
		for(Entity i:entityList) {
            if(i == null){
                break;
            }
			i.setChanged(0);
		}
		for(Entity e: entityList) {
            if(e == null){
                break;
            }
			e.update(this);
        }
        if (this.goal != null) {
            this.goal.update(this);
        }
        
    }
    
    

    
    //--------------------------Strategy Pattern-----------------------------------------

    public boolean whethercouldmoveup(Dungeon dungeon, int x, int y) {
        if (! this.status) {
            return false;
        }
        boolean could = true;
    	List<Entity> EntityList = dungeon.getEntity();
    	for (Entity i: EntityList) {
    		if( (i != null && i.getX()==x) && (i.getY() == y-1) && (i instanceof Door)){
                Door door = (Door) i;
                //0 = open or 1 = close
                if(door.getStatus() == 1){
                    if(this.bag.getKey() == null) {
                        could = false;
                    }else if(this.bag.getKeyId() != door.getId()){
                        could = false;
                    }
                }
    		}
            
            // if there is a boulder. 
    		if( (i != null && i.getX()==x) && (i.getY() == y-1) && (i instanceof Boulder)) {
    			for (Entity e: EntityList) {
                    if(e == null){
                        break;
                    }
                    if((e.getX()==x) && (e.getY() == y-2) && e instanceof Door){
                        Door door = (Door) e;
                        if(door.getStatus() == 1){
                            could = false;
                        }
                    }
    				else if ((e.getX()==x) && (e.getY() == y-2) && ( (e instanceof Wall) || (e instanceof Boulder) ||  (e instanceof Enemy))) {
    					could = false;
    				}
    			}
            }
            
            // if there is a wall
    		if( (i != null && i.getX()==x) && (i.getY() == y-1) && (i instanceof Wall)){
                could = false;
    		}
    	}
    	return could;
    }

    public boolean whethercouldmovedown(Dungeon dungeon, int x, int y) {
        if (! this.status) {
            return false;
        }
    	boolean could = true;
    	List<Entity> EntityList = dungeon.getEntity();
    	for (Entity i: EntityList) {
            if(i == null){
                break;
            }
    		if(i != null &&  (i.getX()==x) && (i.getY() == y+1) && (i instanceof Door)){
                Door door = (Door) i;
                //0 = open or 1 = close
                if(door.getStatus() == 1){
                    if(this.bag.getKey() == null) {
                        could = false;
                    }else if(this.bag.getKeyId() != door.getId()){
                        could = false;
                    }
                }
    		}
            
            // if there is a boulder. 
    		if( i != null && (i.getX()==x) && (i.getY() == y+1) && (i instanceof Boulder)) {
    			for (Entity e: EntityList) {
                    if(e == null){
                        break;
                    }
                    if((e.getX()==x) && (e.getY() == y+2) && e instanceof Door){
                        Door door = (Door) e;
                        if(door.getStatus() == 1){
                            could = false;
                        }
                    }
    				else if ((e.getX()==x) && (e.getY() == y+2) && ((e instanceof Wall) || (e instanceof Boulder) ||  (e instanceof Enemy))) {
    					could = false;
    				}
    			}
            }
            
            // if there is a wall
    		if( (i != null && i.getX()==x) && (i.getY() == y+1) && (i instanceof Wall)){
    			could = false;
    		}
    	}
    	return could;
    }
    
    public boolean whethercouldmoveleft(Dungeon dungeon, int x, int y) {
        if (! this.status) {
            return false;
        }
    	boolean could = true;
    	List<Entity> EntityList = dungeon.getEntity();
    	for (Entity i: EntityList) {
            if(i == null){
                break;
            }
    		if( (i.getX()==x-1) && (i.getY() == y) && (i instanceof Door)){
                Door door = (Door) i;
                //0 = open or 1 = close
                if(door.getStatus() == 1){
                    if(this.bag.getKey() == null) {
                        could = false;
                    }else if(this.bag.getKeyId() != door.getId()){
                        could = false;
                    }
                }
    		}
            
            // if there is a boulder. 
    		if( (i.getX()==x-1) && (i.getY() == y) && (i instanceof Boulder)) {
    			for (Entity e: EntityList) {
                    if(e == null){
                        break;
                    }
                    if((e.getX()==x-2) && (e.getY() == y) && e instanceof Door){
                        Door door = (Door) e;
                        if(door.getStatus() == 1){
                            could = false;
                        }
                    }
    				else if ( (e.getX()==x-2) && (e.getY() == y) && ( (e instanceof Wall) || (e instanceof Boulder) ||  (e instanceof Enemy))) {
    					could = false;
    				}
    			}
            }
            
            // if there is a wall
    		if( (i.getX()==x-1) && (i.getY() == y) && (i instanceof Wall)){
                
    			could = false;
    		}
    	}
    	return could;
    }

    public boolean whethercouldmoveright(Dungeon dungeon, int x, int y) {
        if (! this.status) {
            return false;
        }
    	boolean could = true;
    	List<Entity> EntityList = dungeon.getEntity();
    	for (Entity i: EntityList) {
            if(i == null){
                break;
            }
    		if( (i.getX()==x +1) && (i.getY() == y) && (i instanceof Door)){
                Door door = (Door) i;
                //0 = open or 1 = close
                if(door.getStatus() == 1){
                    if(this.bag.getKey() == null) {
                        could = false;
                    }else if(this.bag.getKeyId() != door.getId()){
                        could = false;
                    }
                }
    		}
            
            // if there is a boulder. 
    		if( (i.getX()==x+1) && (i.getY() == y) && (i instanceof Boulder)) {
    			for (Entity e: EntityList) {
                    if(e == null){
                        break;
                    }
                    if((e.getX()==x+2) && (e.getY() == y) && e instanceof Door){
                        Door door = (Door) e;
                        if(door.getStatus() == 1){
                            could = false;
                        }
                    }
    				else if ( (e.getX()==x+2) && (e.getY() == y) && ( (e instanceof Wall) || (e instanceof Boulder) ||  (e instanceof Enemy))) {
                        could = false;
    				}
    			}
            }
            
            // if there is a wall
    		if( (i.getX()==x + 1) && (i.getY() == y) && (i instanceof Wall)){
    			could = false;
    		}
        }
    	return could;
    }
    
    @Override
    public void moveUp() {
        // if flag = 0, the up square is not a portal, if flag = 1, the up square is a portal
    	int flag = 0;
    	for(Entity i: entityList) {
            if(i == null){
                break;
            }
        	if(i.getX()==this.getX() && i.getY() == (this.getY() - 1) && (i instanceof Portal)) {
        		Portal p = (Portal) i;
        		p.setStatus(1);
        		for(Entity a: entityList) {
                    if(a == null){
                        break;
                    }
                	if(a instanceof Portal && ((Portal) a).getStatus() == 0 && ((Portal) a).getId() == p.getId()) {
                		Portal portal = (Portal) a;
            			x().set(portal.getX());
                        y().set(portal.getY());
                        p.setStatus(0);
            			portal.setStatus(1);
            			flag = 1;
            			break;
                	}
                }
        		break;
        	}
        }
        // if flag = 0, the up square is not a portal.
        // getY() > 0 check player is not at the first line.
        // whethercouldmoveup check the up square able to be move.
        if (flag == 0 && getY() > 0 && whethercouldmoveup(this.getDungeon(), this.getX(), this.getY())) {
            y().set(getY() - 1);
            this.setLastMove("Up");
            if(this.attackmode instanceof Invicible) {
                if(this.bag.getInviciblepotion()==0) {
                    if (this.bag.getSword() != null) {
                        int times = this.bag.getSwordTime();
                        this.makeArmed();
                        this.bag.setSwordTime(times);
                    }
                    else {
                        this.makeUnarmed();
                    }
                }
            }
            notifyPickEntityObservers();
        }
    }
    
    @Override
    public void moveDown() {
        // if flag = 0, the up square is not a portal, if flag = 1, the up square is a portal
    	int flag = 0;
    	for(Entity i: entityList) {
            
            if(i == null){
                break;
            }
           
        	if(i.getX()==this.getX() && i.getY() == (this.getY() + 1) && (i instanceof Portal)) {
                Portal p = (Portal) i;
        		p.setStatus(1);
        		for(Entity a: entityList) {
                    if(a == null){
                        break;
                    }
                	if(a instanceof Portal && ((Portal) a).getStatus() == 0 && ((Portal) a).getId() == p.getId()) {
                		Portal portal = (Portal) a;
            			x().set(portal.getX());
                        y().set(portal.getY());
                        p.setStatus(0);
            			portal.setStatus(1);
            			flag = 1;
            			break;
                	}
                }
        		break;
        	}
        }
        // if flag = 0, the up square is not a portal.
        // getY() > 0 check player is not at the first line.
        // whethercouldmoveup check the up square able to be move.
        
        if (flag == 0 && getY() < (this.dungeon.getHeight() - 1) && whethercouldmovedown(this.getDungeon(), this.getX(), this.getY())) {
            y().set(getY() + 1);
            this.setLastMove("Down");
            if(this.attackmode instanceof Invicible) {
                if(this.bag.getInviciblepotion()==0) {
                    if (this.bag.getSword() != null) {
                        int times = this.bag.getSwordTime();
                        this.makeArmed();
                        this.bag.setSwordTime(times);
                    }
                    else {
                        this.makeUnarmed();
                    }
                }
            }
            notifyPickEntityObservers();
        }
    }
    
    @Override
    public void moveLeft() {
        // if flag = 0, the up square is not a portal, if flag = 1, the up square is a portal
    	int flag = 0;
    	for(Entity i: entityList) {
            if(i == null){
                break;
            }
        	if(i.getX()==this.getX()-1 && i.getY() == (this.getY()) && (i instanceof Portal)) {
        		Portal p = (Portal) i;
        		p.setStatus(1);
        		for(Entity a: entityList) {
                    if(a == null){
                        break;
                    }
                	if(a instanceof Portal && ((Portal) a).getStatus() == 0 && ((Portal) a).getId() == p.getId()) {
                		Portal portal = (Portal) a;
            			x().set(portal.getX());
                        y().set(portal.getY());
                        p.setStatus(0);
            			portal.setStatus(1);
            			flag = 1;
            			break;
                	}
                }
        		break;
        	}
        }
        // if flag = 0, the up square is not a portal.
        // getY() > 0 check player is not at the first line.
        // whethercouldmoveup check the up square able to be move.
        if (flag == 0 && getX() > 0 && whethercouldmoveleft(this.getDungeon(), this.getX(), this.getY())) {
            x().set(getX() - 1);
            this.setLastMove("Left");
            if(this.attackmode instanceof Invicible) {
                if(this.bag.getInviciblepotion()==0) {
                    if (this.bag.getSword() != null) {
                        int times = this.bag.getSwordTime();
                        this.makeArmed();
                        this.bag.setSwordTime(times);
                    }
                    else {
                        this.makeUnarmed();
                    }
                }
            }
            notifyPickEntityObservers();
        }
    }

    @Override
    public void moveRight() {
        // if flag = 0, the up square is not a portal, if flag = 1, the up square is a portal
    	int flag = 0;
    	for(Entity i: entityList) {
            if(i == null){
                break;
            }
        	if(i.getX()==this.getX()+1 && i.getY() == (this.getY()) && (i instanceof Portal)) {
        		Portal p = (Portal) i;
        		p.setStatus(1);
        		for(Entity a: entityList) {
                    if(a == null){
                        break;
                    }
                	if(a instanceof Portal && ((Portal) a).getStatus() == 0 && ((Portal) a).getId() == p.getId()) {
                		Portal portal = (Portal) a;
            			x().set(portal.getX());
                        y().set(portal.getY());
                        p.setStatus(0);
            			portal.setStatus(1);
            			flag = 1;
            			break;
                	}
                }
        		break;
        	}
        }
        // if flag = 0, the up square is not a portal.
        // getY() > 0 check player is not at the first line.
        // whethercouldmoveup check the up square able to be move.
        if (flag == 0 && getX() < this.dungeon.getWidth() - 1 && whethercouldmoveright(this.getDungeon(), this.getX(), this.getY())) {
            x().set(getX() + 1);
            this.setLastMove("Right");
            if(this.attackmode instanceof Invicible) {
                if(this.bag.getInviciblepotion()==0) {
                    if (this.bag.getSword() != null) {
                        int times = this.bag.getSwordTime();
                        this.makeArmed();
                        this.bag.setSwordTime(times);
                    }
                    else {
                        this.makeUnarmed();
                    }
                }
            }
            notifyPickEntityObservers();
        }
    

    }

    //--------------------------State Pattern-----------------------------------------

    public void makeUnarmed() {
        if (! this.status) {
            return;
        }
        this.attackmode = attackmode.becomeUnarmed(this);
        this.type = "unarmed";
    }
    
    public void makeArmed() {
        if (! this.status) {
            return;
        }
        this.attackmode = attackmode.becomeArmed(this);
        this.type = "armed";
    }
    
    public void makeInvicible() {
        if (! this.status) {
            return;
        }
    	this.attackmode = attackmode.becomeInvicible(this);
    	Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
    		private int count = 5;
    		public void run() {
    			if(count > 0) {
    				timedown();
    				count--;
    			}else {
    				cancel();
    			}
    			
    		}
    	};
    	timer.schedule(task, 1000L, 1000L);
    	this.type = "invicible";
    }

    public void makeDead() {
        this.status = false;
        System.out.println("Game Over, You Died");
    }

    public void timedown() {
    	this.bag.setInviciblepotion(this.bag.getInviciblepotion() - 1);
    }
    
    public Attack getAttackmode() {
    	return this.attackmode;
    }
    public Player getPlayer() {
    	return this;
    }
    public void placebomb() {
    	boolean placeable = true;
    	for (Entity e : this.entityList) {
    		if((e.getX() == this.getX()+1) && (e.getY() == this.getY()) && ( (e instanceof Door) || (e instanceof Wall) || (e instanceof Portal)||(e instanceof Key)||(e instanceof Boulder)) ) {
    			placeable = false;
    		}
        }
    	if(placeable && this.bag.getBomb() != null) {	
            Bomb b = new Bomb(dungeon, this.getX()+1,this.getY());
            b.setBombStatus(1);
            dungeon.addEntity(b);
            this.getBag().setBomb(null);
            Timer time = new Timer();
            TimerTask task = new TimerTask() {
                private int count = 4;
                public void run() {
                    if(count > 1) {
                        count --;
                    }else if(count == 1) {
                        b.attack(getPlayer(), b.getX(), b.getY());
                        count --;
                    }else{
                        b.setBombStatus(0);
                        entityList.remove(b);
                        cancel();
                    }
                    
                }
            };
            time.schedule(task, 1000L, 1000L);
        }	
    }

    public void throwsword() {
    	boolean throwable = true;
    	if(this.bag.getSword()== null) {
    		throwable = false;
    	}
    	for (Entity e : this.entityList) {
            if(e == null){
                break;
            }
    		if((e.getX() == this.getX()+1) && (e.getY() == this.getY()) && ( (e instanceof Door) || (e instanceof Wall) || (e instanceof Boulder)) ) {
    			throwable = false;
    		}
        }
    	if(throwable) {
            this.getBag().getSword().x().set(this.getX()+1);
            this.getBag().getSword().y().set(this.getY());
            Sword sword = this.getBag().getSword();
            dungeon.addEntity(sword);
            this.getBag().setSword(null);
    		if(this.attackmode instanceof Invicible) {
    		}
    		else {
    			this.makeUnarmed();
    		}
    	}
    	
    }

    public boolean checkStatus(){
        if (this.attackmode instanceof Invicible) {
            if (this.getBag().getEnd().equals(LocalTime.now()) || this.getBag().getEnd().isBefore(LocalTime.now())) {
                if (this.getBag().getSword() == null) {
                    this.makeUnarmed();
                    return true;
                }
                this.makeArmed();
                return true;
            }
        }
        return false;
    }
}
