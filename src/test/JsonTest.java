package test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import unsw.dungeon.*;

public class JsonTest {
    JSONObject jsonObj;

    @Before
    public void before() {
        jsonObj = new JSONObject();
        JSONArray entities = new JSONArray();
        jsonObj.put("width", 10);
        jsonObj.put("height", 10);
        String entitiesStr = "[";
        
        // set entities
        // set walls
        for(int i = 0; i < 10; i++) {
            String s = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 0,",
                  "\"y\": " + i + ",",
                  "\"type\": \"wall\"",
                "},"
            );
            entitiesStr = entitiesStr + s;
		}
		for(int i = 1; i < 10; i++) {
            String s = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": " + i + ",",
                  "\"y\": 0,",
                  "\"type\": \"wall\"",
                "},"
            );
            entitiesStr = entitiesStr + s;
        }
        for(int i = 1; i < 9; i++) {
            String s = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 9,",
                  "\"y\": " + i + ",",
                  "\"type\": \"wall\"",
                "},"
            );
            entitiesStr = entitiesStr + s;
		}
		for(int i = 1; i < 9; i++) {
            String s = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": " + i + ",",
                  "\"y\": 9,",
                  "\"type\": \"wall\"",
                "},"
            );
            entitiesStr = entitiesStr + s;
        }
        String player = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 1,",
                  "\"y\": 1,",
                  "\"type\": \"player\"",
                "},"
            );
        String boulder = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 1,",
                  "\"y\": 2,",
                  "\"type\": \"boulder\"",
                "},"
            );
        String enemy = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 5,",
                  "\"y\": 1,",
                  "\"type\": \"enemy\"",
                "},"
            );
        String exit = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 7,",
                  "\"y\": 7,",
                  "\"type\": \"exit\"",
                "},"
            );
        String bluekey = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 5,",
                  "\"y\": 3,",
                  "\"id\": 1,",
                  "\"type\": \"key\"",
                "},"
            );

        String bluedoor = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 5,",
                  "\"y\": 4,",
                  "\"id\": 1,",
                  "\"type\": \"door\"",
                "},"
            );

        String potion = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 5,",
                  "\"y\": 7,",
                  "\"type\": \"potion\"",
                "},"
            );
        String portalOne = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 2,",
                  "\"y\": 2,",
                  "\"id\": 1,",
                  "\"type\": \"portal\"",
                "},"
            );
        String portalTwo = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 5,",
                  "\"y\": 2,",
                  "\"id\": 1,",
                  "\"type\": \"portal\"",
                "},"
            );
        String switches = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 1,",
                  "\"y\": 3,",
                  "\"type\": \"switch\"",
                "},"
            );
        String sword = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 2,",
                  "\"y\": 1,",
                  "\"type\": \"sword\"",
                "},"
            );
        String treasure = String.join(
                System.getProperty("line.separator"),
                "{",
                  "\"x\": 6,",
                  "\"y\": 7,",
                  "\"type\": \"treasure\"",
                "},"
            );
        entitiesStr = entitiesStr + player + boulder + enemy + exit +
                        bluekey + bluedoor + potion + portalOne + 
                        portalTwo + switches + sword + treasure;
        
        entitiesStr = entitiesStr + "]";
        
        entities = new JSONArray(entitiesStr);
        jsonObj.put("entities", entities);


    }

    @Test
    public void testExit() {
        // set up goal
        JSONObject subgoal = new JSONObject().put("goal", "exit");
        this.jsonObj.put("goal-condition", subgoal);

        DungeonControllerLoader loader =  new DungeonControllerLoader(this.jsonObj);
        Dungeon dungeon = loader.load();

        // move the player to the right and kill enemies
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        dungeon.getPlayer().moveLeft();
        dungeon.getPlayer().moveLeft();

        // move the player to push boulder to switch
        dungeon.getPlayer().moveDown();

        // move the player through the portal
        dungeon.getPlayer().moveRight();

        // move the player to get the key and open the door
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();

        // move the player to get the potion
        dungeon.getPlayer().moveDown();

        // move the player to get the treasure
        dungeon.getPlayer().moveRight();

        // move the player to exit
        dungeon.getPlayer().moveRight();

        Assert.assertEquals(true, dungeon.getGoal().getGoal());

    }  
    
    @Test
    public void testTreasure() {
        // set up goal
        JSONObject subgoal = new JSONObject().put("goal", "treasure");
        this.jsonObj.put("goal-condition", subgoal);

        DungeonControllerLoader loader =  new DungeonControllerLoader(this.jsonObj);
        Dungeon dungeon = loader.load();

        // move the player to the right and kill enemies
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        dungeon.getPlayer().moveLeft();
        dungeon.getPlayer().moveLeft();

        // move the player to push boulder to switch
        dungeon.getPlayer().moveDown();

        // move the player through the portal
        dungeon.getPlayer().moveRight();

        // move the player to get the key and open the door
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();
        dungeon.getPlayer().moveDown();

        // move the player to get the potion
        dungeon.getPlayer().moveDown();

        // move the player to get the treasure
        dungeon.getPlayer().moveRight();

        Assert.assertEquals(true, dungeon.getGoal().getGoal());

    }

    @Test
    public void testBoulders() {
        // set up goal
        JSONObject subgoal = new JSONObject().put("goal", "boulders");
        this.jsonObj.put("goal-condition", subgoal);

        DungeonControllerLoader loader =  new DungeonControllerLoader(this.jsonObj);
        Dungeon dungeon = loader.load();

        // move the player to the right and kill enemies
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();

        dungeon.getPlayer().moveLeft();
        dungeon.getPlayer().moveLeft();

        // move the player to push boulder to switch
        dungeon.getPlayer().moveDown();

        Assert.assertEquals(true, dungeon.getGoal().getGoal());

    }

    @Test
    public void testEnemy() {
        // set up goal
        JSONObject subgoal = new JSONObject().put("goal", "enemies");
        this.jsonObj.put("goal-condition", subgoal);

        DungeonControllerLoader loader =  new DungeonControllerLoader(this.jsonObj);
        Dungeon dungeon = loader.load();

        // move the player to the right and kill enemies
        dungeon.getPlayer().moveRight();
        dungeon.getPlayer().moveRight();


        Assert.assertEquals(true, dungeon.getGoal().getGoal());

    }
    
    @Test
    public void testComplex() {
      // set up the goal
      JSONArray subGoalArr = new JSONArray();
      String subGoalStr = String.join(
                System.getProperty("line.separator"),
                "[ { \"goal\": \"exit\" },",
                  "{ \"goal\": \"OR\", \"subgoals\":",
                    "[ {\"goal\": \"enemies\"},",
                    "{\"goal\": \"treasure\"}",
                    "]",
                  "}",
                "]"
            );
      subGoalArr = new JSONArray(subGoalStr);
      JSONObject subgoal = new JSONObject().put("goal", "AND");
      subgoal.put("subgoals", subGoalArr);
      jsonObj.put("goal-condition", subgoal);

      DungeonControllerLoader loader =  new DungeonControllerLoader(this.jsonObj);
      Dungeon dungeon = loader.load();

      // move the player to the right and kill enemies
      dungeon.getPlayer().moveRight();
      dungeon.getPlayer().moveRight();

      dungeon.getPlayer().moveLeft();
      dungeon.getPlayer().moveLeft();

      // move the player to push boulder to switch
      dungeon.getPlayer().moveDown();

      // move the player through the portal
      dungeon.getPlayer().moveRight();

      // move the player to get the key and open the door
      dungeon.getPlayer().moveDown();
      dungeon.getPlayer().moveDown();
      dungeon.getPlayer().moveDown();
      dungeon.getPlayer().moveDown();

      // move the player to get the potion
      dungeon.getPlayer().moveDown();

      // move the player to get the treasure
      dungeon.getPlayer().moveRight();

      // move the player to exit
      dungeon.getPlayer().moveRight();

      Assert.assertEquals(true, dungeon.getGoal().getGoal());
    }
}