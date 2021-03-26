package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;

public class PlayerPickUpTest {
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
	public void testPickUp() {
		// -------test pick up one sword -------
		// set Sword
		Sword sword = new Sword(dungeon,1, 2);
		dungeon.addEntity(sword);
		Key key = new Key(dungeon,1, 4, 0);
		dungeon.addEntity(key);

		// move down to pick up sword
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();

		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(3, dungeon.getPlayer().getY());
		
		Assert.assertNotNull(dungeon.getPlayer().getBag().getSword());
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
		
		// test sword is picked and removed from dungeon
		Entity s = null;
		for(Entity e : dungeon.getEntity()){
			if(e instanceof Sword){
				System.out.println("have sword");
				s = e;
			}
		}
		Assert.assertNull(s);
		// ------- test pick up one key ----------

		// continue move down to pick up key
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();

		Assert.assertNotNull(dungeon.getPlayer().getBag().getKey());
		Assert.assertEquals(0, (dungeon.getPlayer().getBag().getKeyId()));

		// test key is picked and removed from dungeon
		Entity i = null;
		for(Entity e : dungeon.getEntity()){
			if(e instanceof Key){
				System.out.println("have key");
				i = e;
			}
		}
		Assert.assertNull(i);

    }
    

	@Test
	public void testPickUpFail() {
		
		//------ test cannot duplicate pick up sword
		// set a sword just can use one time
		Sword sword = new Sword(dungeon,1, 2);
		sword.setUsedtimes(1);
		dungeon.addEntity(sword);
		// move down player to pick up
		dungeon.getPlayer().moveDown();

		// set a new sword which can use 5 times
		Sword sword1 = new Sword(dungeon, 1, 3);
		dungeon.addEntity(sword1);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getBag().getSwordTime());
		
		
		
		//-------test cannot duplicate pick up key
		// set one key
		Key key = new Key(dungeon,1, 4, 0);
		dungeon.addEntity(key);
		// continue move down to pick up key
		dungeon.getPlayer().moveDown();
		
		// cannot duplicate pick up,
		// set another key
		Key key1 = new Key(dungeon, 1, 5, 1);
		dungeon.addEntity(key1);
		// continue move down to pick up key
		dungeon.getPlayer().moveDown();
		// cannot duplicate pick up, key number should still 1
		Assert.assertNotNull(dungeon.getPlayer().getBag().getKey());
		Assert.assertEquals(0, (dungeon.getPlayer().getBag().getKeyId()));
		
	}

	@Test
	public void testCollectTreasure(){
		Treasure treasure1 = new Treasure(dungeon, 1,2);
		dungeon.addEntity(treasure1);
		dungeon.getPlayer().moveDown();
		Entity t = null;
		for(Entity i : dungeon.getEntity()){
			if(i instanceof Treasure){
				t = i;
			}
		}
		Assert.assertNull(t);
	}
}