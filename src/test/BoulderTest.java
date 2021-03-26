package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;

public class BoulderTest {
    
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
	public void testPushBoulderAndCoverSwitch() {
		Switch switchs = new Switch(1, 3);
		Boulder boulder = new Boulder(dungeon, 1, 2);
		dungeon.addEntity(boulder);
		dungeon.addEntity(switchs);
		
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, boulder.getX());
		Assert.assertEquals(3, boulder.getY());
		int test = 0;
		for(Entity i : dungeon.getEntity()) {
			if(i instanceof Switch) {
				test = i.getCovered();
			}
		}
		Assert.assertEquals(1, test);

		Switch switch1 = new Switch(3, 1);
		Boulder boulder1 = new Boulder(dungeon, 2, 1);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(switch1);
		dungeon.getPlayer().moveUp();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(2, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		int test1 = 0;
		for(Entity i : dungeon.getEntity()) {
			if(i instanceof Switch && i.getX() == 3 && i.getY() == 1) {
				test1 = i.getCovered();
			}
		}
		Assert.assertEquals(1, test1);

	}

	@Test
	public void testCannotPushTwoBoulders() {
		Boulder boulder = new Boulder(dungeon, 1, 2);
		dungeon.addEntity(boulder);
		Boulder boulder1 = new Boulder(dungeon, 1, 3);
		dungeon.addEntity(boulder1);
		
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
	}
	
	
	@Test
	public void testCannotPushBoulderNearbyDoor() {
		Boulder boulder = new Boulder(dungeon, 1, 2);
		dungeon.addEntity(boulder);
		Door door = new Door(dungeon, 1, 3,0);
		dungeon.addEntity(door);
		
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, boulder.getX());
		Assert.assertEquals(2, boulder.getY());
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
	}

	@Test
	public void testCannotPushBoulderNearbyWall() {
		Boulder boulder = new Boulder(dungeon, 1, 2);
		dungeon.addEntity(boulder);
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(1, boulder.getX());
		Assert.assertEquals(2, boulder.getY());
		Assert.assertEquals(2, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());
		
	}

	@Test
	public void testCannotPushBoulderNearbyEnemy() {
		Boulder boulder = new Boulder(dungeon, 1, 2);
		dungeon.addEntity(boulder);
		Enemy enemy = new Enemy(dungeon, 1,3);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveDown();	
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		
	}
}