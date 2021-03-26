package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entity.*;
import unsw.dungeon.goal.*;

public class ExitTest {
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
    public void testFinishGoal() {
        // set up the goal
        ExitGoal exitGoal = new ExitGoal("Getting to an exit", dungeon);
        
        // set up the test environment
        Exit exit = new Exit(1, 2);
        dungeon.addEntity(exit);

        //move the player to the exit
        dungeon.getPlayer().moveDown();
        
        // check the goal should be complete
        Assert.assertEquals(true, this.dungeon.getGoal().getGoal());
    }

    @Test
    public void testNotFinishGoal() {
        // set up the goal
        TreasureGoal treasureGoal = new TreasureGoal("Collecting all treasure", dungeon);

        // set up the environment
        Treasure treasureOne = new Treasure(dungeon, 2, 1);
        Treasure treasureTwo = new Treasure(dungeon, 2, 3);
        Exit exit = new Exit(1, 2);
        dungeon.addEntity(treasureOne);
        dungeon.addEntity(treasureTwo);
        dungeon.addEntity(exit);

        // move the player to the exit
        dungeon.getPlayer().moveDown();

        // check the goal should not be complete
        Assert.assertEquals(false, this.dungeon.getGoal().getGoal());
    }

}