package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.entity.*;
import unsw.dungeon.goal.*;
// import unsw.dungeon.goal.*;
/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    public DungeonLoader(JSONObject json) {
        this.json = json;
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        JSONObject goalCon = json.getJSONObject("goal-condition");
        String goalName = goalCon.getString("goal");

        if (goalName.equals("OR")) {
            JSONArray subGoal = goalCon.getJSONArray("subgoals");
            loadComplexGoal(dungeon, subGoal, false);
        } else if (goalName.equals("AND")) {
            JSONArray subGoal = goalCon.getJSONArray("subgoals");
            loadComplexGoal(dungeon, subGoal, true);
        } else {
            loadBaiscGoal(dungeon, goalName);
        }

        return dungeon;
    }

    private Goal loadComplexGoal(Dungeon dungeon, JSONArray subGoal, boolean allNeedFinish) {
        ComplexGoal complexGoal = new ComplexGoal("name", dungeon, allNeedFinish);
        for (int i = 0; i < subGoal.length(); i++) {
            JSONObject sub = subGoal.getJSONObject(i);
            String subName = sub.getString("goal");
            Goal newSub;
            if (subName.equals("OR")) {
                JSONArray newGoal = sub.getJSONArray("subgoals");
                newSub = loadComplexGoal(dungeon, newGoal, false);
            } else if (subName.equals("AND")) {
                JSONArray newGoal = sub.getJSONArray("subgoals");
                newSub = loadComplexGoal(dungeon, newGoal, true);
            } else {
                newSub = loadBaiscGoal(dungeon, subName);
            }

            complexGoal.addGoal(newSub);
        }
        return complexGoal;
    }

    private Goal loadBaiscGoal(Dungeon dungeon, String goalName) {
        switch (goalName) {
            case "exit":
                ExitGoal exitGoal = new ExitGoal("exit", dungeon);
                return exitGoal;
            case "enemies":
                EnemyGoal enemyGoal = new EnemyGoal("enemies", dungeon);
                return enemyGoal;
            case "treasure":
                TreasureGoal treasureGoal = new TreasureGoal("treasure", dungeon);
                return treasureGoal;
            case "boulders":
                SwitchGoal switchGoal = new SwitchGoal("boulders", dungeon);
                return switchGoal;
        }
        return null;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "key":
            int keyId = json.getInt("id");
            Key key = new Key(dungeon, x, y, keyId);
            onLoad(key);
            entity = key;
            break;
        case "door":
            int doorId = json.getInt("id");
            Door door = new Door(dungeon, x, y, doorId);
            onLoad(door);
            entity = door;
            break;
        case "invincibility":
            Potion potion = new Potion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "portal":
            int portalId = json.getInt("id");
            Portal portal = new Portal(x, y, portalId);
            onLoad(portal);
            entity = portal;
            break;
        case "switch":
            Switch floorswitch = new Switch(x, y);
            onLoad(floorswitch);
            entity = floorswitch;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "blackhole":
        	BlackHole hole = new BlackHole(dungeon, x, y);
        	onLoad(hole);
        	entity = hole;
            break;
        case "bomb":
        	Bomb bomb = new Bomb(dungeon, x,y);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        }
        
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Switch floorswitch);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(BlackHole hole);

    public abstract void onLoad(Bomb bomb);
    // TODO Create additional abstract methods for the other entities

}
