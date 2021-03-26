package test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.Bag;
import unsw.dungeon.entity.Boulder;
import unsw.dungeon.entity.Enemy;
import unsw.dungeon.entity.Player;
import unsw.dungeon.entity.Portal;
import unsw.dungeon.entity.Potion;
import unsw.dungeon.entity.Wall;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {
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
	public void testWallBlockPlayer() {
		// player want to move up, from (1,1) to (1,0)
		// it should not work, because (1,0) is wall
		// so player's position should still (1,1);
		dungeon.getPlayer().moveUp();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(8, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(8, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(8, dungeon.getPlayer().getX());
		Assert.assertEquals(8, dungeon.getPlayer().getY());
	}

	@Test
	public void testWallBlockEnemy() {
		Potion potion = new Potion(dungeon, 1, 2);
		dungeon.addEntity(potion);
		
		Enemy enemy = new Enemy(dungeon,1, 6);
        dungeon.addEntity(enemy);
        
        Wall wall = new Wall(1,7);
        dungeon.addEntity(wall);
        Wall wall1 = new Wall(2, 6);
        dungeon.addEntity(wall1);

		dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();

        Assert.assertEquals(1, enemy.getX());
        Assert.assertEquals(6, enemy.getY());
	
	}
	
	@Test
	public void testMoveRight(){
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(2, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		Boulder boulder = new Boulder(dungeon, 3,1);
		dungeon.addEntity(boulder);
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(7, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
	}
	@Test
	public void testMoveLeft(){
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Boulder boulder = new Boulder(dungeon, 3,1);
		dungeon.addEntity(boulder);
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(2, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());

	}
	@Test
	public void testMoveDown(){
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());
		Boulder boulder = new Boulder(dungeon, 1,3);
		dungeon.addEntity(boulder);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(7, dungeon.getPlayer().getY());

	}

	@Test
	public void testMoveUp(){
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveUp();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		Boulder boulder = new Boulder(dungeon, 1,2);
		dungeon.addEntity(boulder);
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveUp();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());

	}
	
	@Test
	public void deadPlayerCannotMove(){
		Enemy enemy = new Enemy(dungeon, 1, 3);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertFalse(dungeon.getPlayer().getStatus());
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());
	}

	@Test

	public void testUpDownPortal() {
		Portal portal = new Portal(1, 2, 1);
		dungeon.addEntity(portal);
		Portal portal1= new Portal(6, 6, 1);
		dungeon.addEntity(portal1);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(6, dungeon.getPlayer().getX());
		Assert.assertEquals(6, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveUp();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());
	}
	@Test
	public void testLeftRightPortal() {
		Portal portal = new Portal(2, 1, 1);
		dungeon.addEntity(portal);
		Portal portal1= new Portal(6, 6, 1);
		dungeon.addEntity(portal1);
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(6, dungeon.getPlayer().getX());
		Assert.assertEquals(6, dungeon.getPlayer().getY());
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(2, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
	}

	@Test
	public void testEnemyTeleportedByPortalRight(){
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(3, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		Portal portal = new Portal(2, 1, 1);
		dungeon.addEntity(portal);
		Portal portal1= new Portal(6, 6, 1);
		dungeon.addEntity(portal1);
		Enemy enemy = new Enemy(dungeon,1,1);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(6, enemy.getX());
		Assert.assertEquals(6, enemy.getY());
	}

	@Test
	public void testEnemyTeleportedByPortalLeft(){
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(3, dungeon.getPlayer().getX());
		Assert.assertEquals(1, dungeon.getPlayer().getY());
		Portal portal = new Portal(4, 1, 1);
		dungeon.addEntity(portal);
		Portal portal1= new Portal(6, 6, 1);
		dungeon.addEntity(portal1);
		Enemy enemy = new Enemy(dungeon,5,1);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals(6, enemy.getX());
		Assert.assertEquals(6, enemy.getY());
	}

	@Test
	public void testEnemyTeleportedByPortalUp(){
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(3, dungeon.getPlayer().getY());
		Portal portal = new Portal(1, 2, 1);
		dungeon.addEntity(portal);
		Portal portal1= new Portal(6, 6, 1);
		dungeon.addEntity(portal1);
		Enemy enemy = new Enemy(dungeon,1,1);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(6, enemy.getX());
		Assert.assertEquals(6, enemy.getY());
	}

	@Test
	public void testEnemyTeleportedByPortalDown(){
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(3, dungeon.getPlayer().getY());
		Portal portal = new Portal(1, 4, 1);
		dungeon.addEntity(portal);
		Portal portal1= new Portal(6, 6, 1);
		dungeon.addEntity(portal1);
		Enemy enemy = new Enemy(dungeon,1,5);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveUp();
		Assert.assertEquals(6, enemy.getX());
		Assert.assertEquals(6, enemy.getY());
	}
	

	
}