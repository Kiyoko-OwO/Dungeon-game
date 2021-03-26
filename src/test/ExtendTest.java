package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;

public class ExtendTest {
    private Dungeon dungeon;

    @Before
    public void before() {
        // create a dungeon
        dungeon = new Dungeon(10,10);
        
        // set wall
        for(int i = 0; i < 10; i++) {
            dungeon.addEntity(new Wall(0, i));
        }
        for(int i = 1; i < 10; i++) {
            dungeon.addEntity(new Wall(i, 0));
        }
        for(int i = 1; i < 9; i++) {
            dungeon.addEntity(new Wall(9, i));
        }
        for(int i = 1; i< 9; i++) {
            dungeon.addEntity(new Wall(i, 9));
        }
        
        // set player
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        
        Bag bag = new Bag();
        player.setBag(bag);
        
    }

    @Test
	public void testPickUpBomb() {
        Bomb bomb = new Bomb(dungeon, 1, 2);
		dungeon.addEntity(bomb);
		// continue move down to pick up bomb
        dungeon.getPlayer().moveDown();
        Assert.assertNotNull(dungeon.getPlayer().getBag().getBomb());
        Entity b = null;
        for(Entity bomb1 : dungeon.getEntity()){
            if(bomb1 instanceof Bomb && (bomb1.getX() == dungeon.getPlayer().getX() && bomb1.getY() == dungeon.getPlayer().getY())){
                b = bomb1;
            }
        }
        Assert.assertNull(b);
    }

    @Test
    public void testCannotPickUpDuplicateBomb(){
        Bomb bomb = new Bomb(dungeon, 1, 2);
		dungeon.addEntity(bomb);
		// continue move down to pick up bomb
        dungeon.getPlayer().moveDown();
        Assert.assertNotNull(dungeon.getPlayer().getBag().getBomb());
        Entity b = null;
        for(Entity bomb1 : dungeon.getEntity()){
            if(bomb1 instanceof Bomb && (bomb1.getX() == dungeon.getPlayer().getX() && bomb1.getY() == dungeon.getPlayer().getY())){
                b = bomb1;
            }
        }
        Assert.assertNull(b);

        Bomb bomb1 = new Bomb(dungeon, 1, 3);
        dungeon.addEntity(bomb1);
        dungeon.getPlayer().moveDown();
        Entity b1 = null;
        for(Entity bomb2 : dungeon.getEntity()){
            if(bomb2 instanceof Bomb && (bomb2.getX() == dungeon.getPlayer().getX() && bomb2.getY() == dungeon.getPlayer().getY())){
                b1 = bomb2;
            }
        }
        Assert.assertNotNull(b1);
    }

    @Test
	public void testBombAttack() {
		// set bomb and all bomb status
		Bomb bomb = new Bomb(dungeon,1, 2);
		dungeon.addEntity(bomb);
		// player get bomb
		dungeon.getPlayer().moveDown();     // now player at 1,2
		Assert.assertNotNull(dungeon.getPlayer().getBag().getBomb());
		dungeon.getPlayer().moveRight();	// now player at 2,2
		dungeon.getPlayer().moveRight();    // now player at 3,2
		dungeon.getPlayer().moveRight();    // now player at 4,2
		dungeon.getPlayer().moveDown();    // now player at 4,3
		dungeon.getPlayer().moveDown();    // now player at 4,4
		
		// set a boulder on 6,4
		Boulder boulder = new Boulder(dungeon, 6, 4);
		dungeon.addEntity(boulder);
		
		// player use bomb, put bomb on 5,4
		dungeon.getPlayer().placebomb();
		System.out.println(dungeon.getPlayer().getX());
		System.out.println(dungeon.getPlayer().getY());
		
		
		for(int i = 5; i >= 1; i--) {
			try {
				//System.out.println(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }	
		Entity boulder1 = null;
        for(Entity boulder2 : dungeon.getEntity()){
            if(boulder2 instanceof Enemy){
                boulder1 = boulder2;
            }
        }
		Assert.assertNull(boulder1);
		
	}
	
	
	@Test
	public void testPlayerDeadByBomb() {
		Bomb bomb = new Bomb(dungeon,1, 2);
		dungeon.addEntity(bomb);
			
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().placebomb();

		for(int i = 5; i >= 1; i--) {
			try {
				//System.out.println(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        Assert.assertFalse(dungeon.getPlayer().getStatus());
	}
	
	@Test
	public void testInvincibePlayerCannotDeadByBomb() {
		Bomb bomb = new Bomb(dungeon,1, 2);
		dungeon.addEntity(bomb);
		
		
		Potion potion = new Potion(dungeon,1, 3);
		dungeon.addEntity(potion);
		
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();// now player at (1,3)
		
		dungeon.getPlayer().placebomb();
		
		for(int i = 5; i >= 1; i--) {
			try {
				//System.out.println(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Assert.assertTrue(dungeon.getPlayer().getStatus());
    }
    
    	
	@Test
	public void testThrow() {
		Sword sword = new Sword(dungeon,1, 2);
		dungeon.addEntity(sword);
		// pick up sword;
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
		// if no wall or boulder, player can throw his sword
		dungeon.getPlayer().throwsword();
		// test no sword in this bag(useTime become 0)
		Assert.assertNull(dungeon.getPlayer().getBag().getSword());
	}
	
    
    
    @Test
	public void testThrowFailByWall() {
		Sword sword = new Sword(dungeon,1, 2);
		dungeon.addEntity(sword);
		// pick up sword
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
		// if there is a wall on the right of the player
		Wall awall = new Wall(dungeon.getPlayer().getX() + 1, dungeon.getPlayer().getY());
		dungeon.addEntity(awall);
		dungeon.getPlayer().throwsword();
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
	}
	
    @Test
	public void testBlackHole() {
		BlackHole hole = new BlackHole(dungeon, 1,2);
		dungeon.addEntity(hole);
		// pick up sword
		dungeon.getPlayer().moveDown();
        Assert.assertFalse(dungeon.getPlayer().getStatus());
        Entity player = null;
        for(Entity p : dungeon.getEntity()){
            if(p instanceof Player){
                player = p;
            }
        }
		Assert.assertNull(player);
	}
}