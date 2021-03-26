package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;

public class PotionTest {
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
	public void testPotionDisappear() {
		Potion potion = new Potion(dungeon, 1, 2);
		dungeon.addEntity(potion);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("invicible", dungeon.getPlayer().getType());

		for(int i = 6; i >= 1; i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("unarmed", dungeon.getPlayer().getType());

	}

	@Test
	public void testMoveRight(){
		Potion potion = new Potion(dungeon, 2, 1);
		dungeon.addEntity(potion);
		dungeon.getPlayer().moveRight();
		Assert.assertEquals("invicible", dungeon.getPlayer().getType());
	}

	@Test
	public void testMoveLeft(){
		Potion potion = new Potion(dungeon, 2, 1);
		dungeon.addEntity(potion);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveRight();
		dungeon.getPlayer().moveUp();
		dungeon.getPlayer().moveLeft();
		Assert.assertEquals("invicible", dungeon.getPlayer().getType());
	}
	@Test
	public void testPotionSwordExchanged() {
		Sword sword = new Sword(dungeon, 1,2);
		dungeon.addEntity(sword);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("armed", dungeon.getPlayer().getType());
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
		Potion potion = new Potion(dungeon, 1, 4);
		dungeon.addEntity(potion);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("invicible", dungeon.getPlayer().getType());
		for(int i = 6; i >= 1; i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("armed", dungeon.getPlayer().getType());
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
	 }

	@Test
	public void testPotionAttackWillNotUseSwordTime() {
		Sword sword = new Sword(dungeon, 1,2);
		dungeon.addEntity(sword);
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("armed", dungeon.getPlayer().getType());
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
		Potion potion = new Potion(dungeon, 1, 4);
		dungeon.addEntity(potion);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals("invicible", dungeon.getPlayer().getType());
		Enemy enemy = new Enemy(dungeon, 1, 5);
		dungeon.addEntity(enemy);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(5, dungeon.getPlayer().getBag().getSwordTime());
		for(int i = 6; i >= 1; i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Enemy enemy1 = new Enemy(dungeon, 1, 7);
		dungeon.addEntity(enemy1);
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		dungeon.getPlayer().moveDown();
		Assert.assertEquals(4, dungeon.getPlayer().getBag().getSwordTime());
	}
}