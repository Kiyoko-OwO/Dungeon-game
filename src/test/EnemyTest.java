package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;

public class EnemyTest {
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
        for(int i = 1; i < 9; i++) {
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
	public void testSwordKillEnemy(){
		Sword sword = new Sword(dungeon, 1,3);
		dungeon.addEntity(sword);

		Enemy enemy = new Enemy(dungeon, 1, 5);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertNotNull(dungeon.getPlayer().getBag().getSword());
		Assert.assertEquals(4, dungeon.getPlayer().getBag().getSwordTime());
		dungeon.getPlayer().moveDown();
		
		Entity i = null;
		for(Entity e : dungeon.getEntity()){
			if(e instanceof Enemy){
				i = e;
			}
		}
		Assert.assertNull(i);
	}

	@Test
	public void testEnemyKillPlayer(){
		Enemy enemy = new Enemy(dungeon, 1, 3);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Entity i = null;
		for(Entity e : dungeon.getEntity()){
			if(e instanceof Enemy){
				i = e;
			}
		}
		Assert.assertNotNull(i);
		Assert.assertEquals(1, dungeon.getPlayer().getX());
		Assert.assertEquals(2, dungeon.getPlayer().getY());
		Assert.assertFalse(dungeon.getPlayer().getStatus());
	}

	@Test
	public void testSwordJustCanUse5times(){
		Sword sword = new Sword(dungeon, 1,2);
		dungeon.addEntity(sword);
		dungeon.getPlayer().moveDown();
		Enemy enemy1 = new Enemy(dungeon, 1, 4);
		dungeon.addEntity(enemy1);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(4, dungeon.getPlayer().getBag().getSwordTime());
		Enemy enemy2 = new Enemy(dungeon, 1, 5);
		dungeon.addEntity(enemy2);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(3, dungeon.getPlayer().getBag().getSwordTime());
		Enemy enemy3 = new Enemy(dungeon, 1, 6);
		dungeon.addEntity(enemy3);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(2, dungeon.getPlayer().getBag().getSwordTime());
		Enemy enemy4 = new Enemy(dungeon, 1, 7);
		dungeon.addEntity(enemy4);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(1, dungeon.getPlayer().getBag().getSwordTime());
		Enemy enemy5 = new Enemy(dungeon, 1, 8);
		dungeon.addEntity(enemy5);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(0, dungeon.getPlayer().getBag().getSwordTime());
		Assert.assertEquals("unarmed", dungeon.getPlayer().getType());
	}
	@Test
	public void testPotionAttack() {
		Potion potion = new Potion(dungeon, 1, 2);
		dungeon.addEntity(potion);
		
		Enemy enemy = new Enemy(dungeon,1, 4);
		dungeon.addEntity(enemy);

		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();

		Entity i = null;
		for(Entity e : dungeon.getEntity()){
			if(e instanceof Enemy){
				i = e;
			}
		}
		Assert.assertNull(i);

	}

	@Test
	public void testEnemyMoveToPlayer(){
		Player player = dungeon.getPlayer();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(5, player.getX());
		Assert.assertEquals(5, player.getY());
		Enemy enemy = new Enemy(dungeon, 1,5);
		Enemy enemy1 = new Enemy(dungeon, 5,1);
		Enemy enemy2 = new Enemy(dungeon, 8,5);
		Enemy enemy3 = new Enemy(dungeon, 5,8);
		dungeon.addEntity(enemy);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(enemy3);
		
		dungeon.getPlayer().moveDown();
		System.out.println(player.getX() + " " + player.getY());
		System.out.println(enemy.getX() + " " + enemy.getY());
		System.out.println(enemy1.getX() + " " + enemy1.getY());
		System.out.println(enemy2.getX() + " " + enemy2.getY());
		System.out.println(enemy3.getX() + " " + enemy3.getY());
		

		// Assert.assertEquals(5, player.getX());
		// Assert.assertEquals(6, player.getY());
		// Assert.assertEquals(2, enemy.getX());
		// Assert.assertEquals(5, enemy.getY());
		// Assert.assertEquals(5, enemy1.getX());
		// Assert.assertEquals(2, enemy1.getY());
		// Assert.assertEquals(8, enemy2.getX());
		// Assert.assertEquals(5, enemy2.getY());
		// Assert.assertEquals(5, enemy3.getX());
		// Assert.assertEquals(8, enemy3.getY());
	}

	@Test
	public void testEnemyMoveAwayInviciblePlayerOne(){
		Potion potion = new Potion(dungeon, 4, 5);
		dungeon.addEntity(potion);
		Player player = dungeon.getPlayer();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		Assert.assertEquals(5, player.getX());
		Assert.assertEquals(5, player.getY());
		Assert.assertEquals("invicible", player.getType());
		Enemy enemy = new Enemy(dungeon, 7,4);
		Enemy enemy1 = new Enemy(dungeon, 4,7);
		Enemy enemy2 = new Enemy(dungeon, 4,1);
		Enemy enemy3 = new Enemy(dungeon, 1,4);
		dungeon.addEntity(enemy);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(enemy3);
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveLeft();
		dungeon.getPlayer().moveLeft();
		System.out.println(player.getX() + " " + player.getY());
		System.out.println(enemy.getX() + " " + enemy.getY());
		System.out.println(enemy1.getX() + " " + enemy1.getY());
		System.out.println(enemy2.getX() + " " + enemy2.getY());
		System.out.println(enemy3.getX() + " " + enemy3.getY());
		
		// Assert.assertEquals(1, player.getX());
		// Assert.assertEquals(1, player.getY());
		// Assert.assertEquals(8, enemy.getX());
		// Assert.assertEquals(8, enemy.getY());
		// Assert.assertEquals(1, enemy1.getX());
		// Assert.assertEquals(8, enemy1.getY());
		// Assert.assertEquals(1, enemy2.getX());
		// Assert.assertEquals(1, enemy2.getY());
		// Assert.assertEquals(1, enemy3.getX());
		// Assert.assertEquals(8, enemy3.getY());
	}

	@Test
	public void testEnemyMoveAwayInviciblePlayerTwo(){
		Potion potion = new Potion(dungeon, 1, 2);
		dungeon.addEntity(potion);
		Enemy enemy = new Enemy(dungeon, 1,5);
		Enemy enemy1 = new Enemy(dungeon, 5,1);
		Enemy enemy2 = new Enemy(dungeon, 8,5);
		Enemy enemy3 = new Enemy(dungeon, 5,8);
		dungeon.addEntity(enemy);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(enemy3);

		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();

		Assert.assertEquals(1, enemy.getX());
		Assert.assertEquals(8, enemy.getY());
		Assert.assertEquals(8, enemy1.getX());
		Assert.assertEquals(1, enemy1.getY());
		Assert.assertEquals(8, enemy2.getX());
		Assert.assertEquals(8, enemy2.getY());
		Assert.assertEquals(8, enemy3.getX());
		Assert.assertEquals(8, enemy3.getY());
	}
}