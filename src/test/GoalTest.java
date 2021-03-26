package test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;
import unsw.dungeon.goal.*;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GoalTest {
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
    public void testEnemyGoal() {
        // set up the goal
        EnemyGoal enemyGoal = new EnemyGoal("Destroying all enemies", dungeon);

        // set up the test environment
        Sword sword = new Sword(dungeon, 2, 1);
        Enemy enemyOne = new Enemy(dungeon, 5, 1);
        Enemy enemyTwo = new Enemy(dungeon, 6, 1);
        dungeon.addEntity(sword);
        dungeon.addEntity(enemyOne);
        dungeon.addEntity(enemyTwo);

        // check the goal is not complete
        Assert.assertEquals(false, enemyGoal.getGoal());

        // move the player to get sword and kill the enemies
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        // check the goal should be complete
        Assert.assertEquals(true, enemyGoal.getGoal());


    }

    @Test
    public void testEnemyGoalNoEnemy() {
        // set up the goal
        EnemyGoal enemyGoal = new EnemyGoal("Destroying all enemies", dungeon);

        // if there is no enemy, achieve the enemy goal
        dungeon.getPlayer().moveRight();
        Assert.assertEquals(true, enemyGoal.getGoal());

    }


    @Test
    public void testExitGoal() {
        // set up the goal
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);

        // if there is no exit, just return false
        Assert.assertEquals(false, exitGoal.getGoal());

        // set up the test environment
        Exit exit = new Exit(1, 2);
        dungeon.addEntity(exit);

        //move the player to the exit
        dungeon.getPlayer().moveDown();
        
        

        // check the goal should be complete
        Assert.assertEquals(true, exitGoal.getGoal());

    }

    @Test
    public void testSwitchGoal() {
        // set up the goal
        SwitchGoal switchGoal = new SwitchGoal("Having a boulder on all floor switches", dungeon);

        // set up the environment
        Boulder boulderOne = new Boulder(dungeon, 1, 2);
        Switch switchOne = new Switch(1, 4);
        Boulder boulderTwo = new Boulder(dungeon, 3, 4);
        Switch switchTwo = new Switch(5, 4);
        dungeon.addEntity(boulderOne);
        dungeon.addEntity(switchOne);
        dungeon.addEntity(boulderTwo);
        dungeon.addEntity(switchTwo);

        // move the player to push the boulderOne to switchOne
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        Assert.assertEquals(false, switchGoal.getGoal());
        

        // move the player to push boulderTwo to switchTwo
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        

        // check the goal should be complete
        Assert.assertEquals(true, switchGoal.getGoal());
    }

    @Test
    public void testEnemyGoalNoSwitch() {
        // set up the goal
        SwitchGoal switchGoal = new SwitchGoal("Having a boulder on all floor switches", dungeon);

        // if there is no switch, achieve the switch goal
        dungeon.getPlayer().moveDown();
        Assert.assertEquals(true, switchGoal.getGoal());
    }
    
    @Test
    public void testTreasureGoal() {
        // set up the goal
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);

        // set up the environment
        Treasure treasureOne = new Treasure(dungeon, 2, 1);
        Treasure treasureTwo = new Treasure(dungeon, 2, 3);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);

        // move the player to get the treasure
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();


        // check the goal should be complete
        Assert.assertEquals(true, treasureGoal.checkGoal());
    }

    @Test
    public void testTreasureGoalNoTreasure() {
        // set up the goal
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);

        // if there is no treasure, achieve the treasure goal
        dungeon.getPlayer().moveRight();
        Assert.assertEquals(true, treasureGoal.getGoal());
    }

    @Test
    public void testComplexGoalAndOne() {
        // set up the goal
        ComplexGoal complexGoal = new ComplexGoal("destroys all enemies and gets to an exit", dungeon, true);
        EnemyGoal enemyGoal = new EnemyGoal("Destroying all enemies", dungeon);
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);
        complexGoal.addGoal(enemyGoal, exitGoal);

        // set up the test environment
        Sword sword = new Sword(dungeon, 2, 1);
        Enemy enemyOne = new Enemy(dungeon, 4, 1);
        Enemy enemyTwo = new Enemy(dungeon, 5, 1);
        Exit exit = new Exit(5, 2);
        dungeon.addEntity(sword);
        dungeon.addEntity(enemyOne);
        dungeon.addEntity(enemyTwo);
        dungeon.addEntity(exit);

        // move the player to get swords and kill the enemies then go to the exit
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveDown();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
    }

    @Test
    public void testComplexGoalAndTwo() {
        // set up the goal
        ComplexGoal complexGoal = new ComplexGoal("gets to an exit and collects all treasure", dungeon, true);
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);
        complexGoal.addGoal(treasureGoal, exitGoal);

        // set up the test environment
        Treasure treasureOne = new Treasure(dungeon, 2, 1);
        Treasure treasureTwo = new Treasure(dungeon, 3, 1);
        Exit exit = new Exit(5, 2);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
        dungeon.addEntity(exit);

        // move the player to get swords and kill the enemies then go to the exit
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveDown();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
    }

    @Test
    public void testComplexGoalOrOne() {
        // set up the goal
        ComplexGoal complexGoal = new ComplexGoal("has a boulder on all floor switches or collects all treasure", dungeon, false);
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);
        SwitchGoal switchGoal = new SwitchGoal("Having a boulder on all floor switches", dungeon);
        complexGoal.addGoal(treasureGoal, switchGoal);

        // set up the environment
        Treasure treasureOne = new Treasure(dungeon, 2, 1);
        Treasure treasureTwo = new Treasure(dungeon, 3, 1);
        Boulder boulderOne = new Boulder(dungeon, 1, 2);
        Switch switchOne = new Switch(1, 4);
        Boulder boulderTwo = new Boulder(dungeon, 3, 4);
        Switch switchTwo = new Switch(5, 4);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
        dungeon.addEntity(boulderOne);
        dungeon.addEntity(switchOne);
        dungeon.addEntity(boulderTwo);
        dungeon.addEntity(switchTwo);

        // move the player to collect all treasure
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
        
    }

    @Test
    public void testComplexGoalOrTwo() {
        // set up the goal
        ComplexGoal complexGoal = new ComplexGoal("has a boulder on all floor switches or collects all treasure", dungeon, false);
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);
        SwitchGoal switchGoal = new SwitchGoal("Having a boulder on all floor switches", dungeon);
        complexGoal.addGoal(treasureGoal, switchGoal);

        // set up the environment
        Treasure treasureOne = new Treasure(dungeon, 2, 1);
        Treasure treasureTwo = new Treasure(dungeon, 3, 1);
        Boulder boulderOne = new Boulder(dungeon, 1, 2);
        Switch switchOne = new Switch(1, 4);
        Boulder boulderTwo = new Boulder(dungeon, 3, 4);
        Switch switchTwo = new Switch(5, 4);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
        dungeon.addEntity(boulderOne);
        dungeon.addEntity(switchOne);
        dungeon.addEntity(boulderTwo);
        dungeon.addEntity(switchTwo);

        // move the player to push all boulders to switches
        dungeon.getPlayer().moveLeft();
        dungeon.getPlayer().moveLeft();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
        
    }

    @Test
    public void testComplexGoalAndOrOne() {
        // set up the goal
        ComplexGoal complexGoal = new ComplexGoal("gets to an exit and either collects all treasure or destroys all enemies", dungeon, true);
        ComplexGoal subComplexGoal = new ComplexGoal("either collects all treasure or destroys all enemies", dungeon, false);
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);
        EnemyGoal enemyGoal = new EnemyGoal("Destroying all enemies", dungeon);
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);
        subComplexGoal.addGoal(enemyGoal, treasureGoal);
        complexGoal.addGoal(exitGoal, subComplexGoal);

        // set up the environment
        Sword sword = new Sword(dungeon, 1, 2);
        Treasure treasureOne = new Treasure(dungeon, 8, 8);
        Treasure treasureTwo = new Treasure(dungeon, 7, 7);
        
        Enemy enemyTwo = new Enemy(dungeon, 1, 8);
        dungeon.addEntity(sword);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
       
        dungeon.addEntity(enemyTwo);
        Exit exit = new Exit(2, 8);
        dungeon.addEntity(sword);

        dungeon.addEntity(exit);

        // move to kill all enemy and go to exit
        
        dungeon.getPlayer().moveDown();
        Assert.assertEquals("armed", dungeon.getPlayer().getType());
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        Assert.assertEquals(4, dungeon.getPlayer().getBag().getSwordTime());
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
     
        dungeon.getPlayer().moveRight();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
    }

    @Test
    public void testComplexGoalAndOrTwo() {
        // set up the goal
        ComplexGoal complexGoal = new ComplexGoal("gets to an exit and either collects all treasure or destroys all enemies", dungeon, true);
        ComplexGoal subComplexGoal = new ComplexGoal("either collects all treasure or destroys all enemies", dungeon, false);
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);
        EnemyGoal enemyGoal = new EnemyGoal("Destroying all enemies", dungeon);
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);
        subComplexGoal.addGoal(enemyGoal, treasureGoal);
        complexGoal.addGoal(exitGoal, subComplexGoal);

        // set up the environment
        Sword sword = new Sword(dungeon, 1, 2);
        Treasure treasureOne = new Treasure(dungeon, 2, 1);
        Treasure treasureTwo = new Treasure(dungeon, 3, 1);
        Enemy enemyOne = new Enemy(dungeon, 4, 4);
        Enemy enemyTwo = new Enemy(dungeon, 2, 4);
        dungeon.addEntity(sword);
        dungeon.addEntity(enemyOne);
        dungeon.addEntity(enemyTwo);
        Exit exit = new Exit(7, 4);
        dungeon.addEntity(sword);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
        dungeon.addEntity(enemyOne);
        dungeon.addEntity(enemyTwo);
        dungeon.addEntity(exit);

        // move to kill all enemies and go to exit
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
    }

    @Test
    public void testComplexGoalAndOrThree() {
        ComplexGoal complexGoal = new ComplexGoal("gets to an exit and either collects all treasure or destroys all enemies", dungeon, true);
        ComplexGoal subComplexGoal = new ComplexGoal("either collects all treasure or destroys all enemies", dungeon, false);
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);
        EnemyGoal enemyGoal = new EnemyGoal("Destroying all enemies", dungeon);
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);
        subComplexGoal.addGoal(enemyGoal, treasureGoal);
        complexGoal.addGoal(exitGoal, subComplexGoal);

        // set up the environment
        Sword sword = new Sword(dungeon, 1, 2);
        Treasure treasureOne = new Treasure(dungeon, 1, 3);
        Treasure treasureTwo = new Treasure(dungeon, 1, 4);
        
        Enemy enemyTwo = new Enemy(dungeon, 1, 8);
        dungeon.addEntity(sword);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
       
        dungeon.addEntity(enemyTwo);
        Exit exit = new Exit(2, 8);
        dungeon.addEntity(sword);

        dungeon.addEntity(exit);

        // move to kill all enemy and go to exit
        
        dungeon.getPlayer().moveDown();
        Assert.assertEquals("armed", dungeon.getPlayer().getType());
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        Assert.assertEquals(4, dungeon.getPlayer().getBag().getSwordTime());
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        Assert.assertEquals(false, complexGoal.getGoal());
        dungeon.getPlayer().moveRight();

        // check the goal should be complete
        Assert.assertEquals(true, complexGoal.getGoal());
    }
}