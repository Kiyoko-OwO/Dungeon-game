package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;

public class DoorTest {
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
	public void testLockedDoorBlockPlayer() {
		Door door = new Door(dungeon, 2, 2, 0);
		dungeon.addEntity(door);
		
		dungeon.getPlayer().moveRight(); // 2,1
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(2, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveRight();//3,1
		dungeon.getPlayer().moveDown();//3,2
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(3, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveDown();//3,3
		dungeon.getPlayer().moveLeft();//2,3
		dungeon.getPlayer().moveUp();//2,3
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());


	}

	@Test
	public void testLockedDoorBlockEnemy() {
		Door door = new Door(dungeon, 1, 4, 0);
		dungeon.addEntity(door);
		Door door1 = new Door(dungeon, 2, 3, 1);
		dungeon.addEntity(door1);
		Door door2 = new Door(dungeon, 1, 2, 3);
		dungeon.addEntity(door2);
		Enemy enemy = new Enemy(dungeon, 1, 3);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveRight(); // 2,1
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(1, enemy.getX());
		Assert.assertEquals(3, enemy.getY());
	}

	// test locked Door can block Boulder is tested in boulder file

	
	@Test
	public void testOpenDoor() {
		// test player and enemy can pass open door
		Key key = new Key(dungeon,3, 1, 0);
		dungeon.addEntity(key);
		
		Door door = new Door(dungeon, 5, 1, 0);
		dungeon.addEntity(door);
		
		dungeon.getPlayer().moveRight();

		dungeon.getPlayer().moveRight();
		// get Key
		Assert.assertNotNull(dungeon.getPlayer().getBag().getKey());
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(5, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(6, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		// key has been used, key number should be 0
		Assert.assertNull(null, dungeon.getPlayer().getBag().getKey());
		dungeon.getPlayer().moveRight();


		Assert.assertEquals(7, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		
		dungeon.getPlayer().moveRight();

		Assert.assertEquals(8, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
	}

	@Test
	public void testWrongKeyCannotOpenDoor() {
		// test player and enemy can pass open door
		Key key = new Key(dungeon,2, 1, 0);
		dungeon.addEntity(key);
		
		Door door = new Door(dungeon, 4, 1, 1);
		dungeon.addEntity(door);
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(3, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		Assert.assertNotNull(dungeon.getPlayer().getBag().getKey());

	}

}