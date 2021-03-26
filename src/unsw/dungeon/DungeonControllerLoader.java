package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.File;

import unsw.dungeon.attackmode.*;
import unsw.dungeon.entity.*;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images

    private Image playerImage;
    private Image invicibleplayerImage;
    private Image swordplayerImage;
    private Image wallImage;
    private Image boulderImage;
    private Image potionImage;
    private Image switchImage;
    private Image enemyImage;
    private Image swordImage;
    private Image keyImage;
    private Image keyblueImage;
    private Image keyredImage;
    private Image closeddoorImage;
    private Image closeddoorblueImage;
    private Image closeddoorredImage;
    private Image openeddoorImage;
    private Image openeddoorblueImage;
    private Image openeddoorredImage;
    private Image bombunlitImage;
    private Image bomblit1Image;
    private Image bomblit2Image;
    private Image bomblit3Image;
    private Image bomblit4Image;
    private Image treasureImage;
    private Image exitImage;
    private Image blackholeImage;
    private Image portalImage;

    public DungeonControllerLoader(JSONObject json) {
        super(json);
    }

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        potionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        keyblueImage = new Image((new File("images/bluekey.png")).toURI().toString());
        keyredImage = new Image((new File("images/redkey.png")).toURI().toString());
        closeddoorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        closeddoorblueImage = new Image((new File("images/closed_bluedoor.png")).toURI().toString());
        closeddoorredImage = new Image((new File("images/closed_reddoor.png")).toURI().toString());
        openeddoorImage = new Image((new File("images/open_door.png")).toURI().toString());
        openeddoorblueImage = new Image((new File("images/open_bluedoor.png")).toURI().toString());
        openeddoorredImage = new Image((new File("images/open_reddoor.png")).toURI().toString());
        bombunlitImage = new Image((new File("images/bomb_unlit.png")).toURI().toString());
        bomblit1Image = new Image((new File("images/bomb_lit_1.png")).toURI().toString());
        bomblit2Image = new Image((new File("images/bomb_lit_2.png")).toURI().toString());
        bomblit3Image = new Image((new File("images/bomb_lit_3.png")).toURI().toString());
        bomblit4Image = new Image((new File("images/bomb_lit_4.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        blackholeImage = new Image((new File("images/blackHole.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
      
    }

    @Override
    public void onLoad(Player player) {
        if(player.getAttackmode() instanceof Unarmed) {
            ImageView view = new ImageView(playerImage);
            addEntity(player, view);
        }
        else if(player.getAttackmode() instanceof Armed) {
            ImageView view = new ImageView(swordplayerImage);
            addEntity(player, view);
        }
        else {
            ImageView view = new ImageView(invicibleplayerImage);
            addEntity(player, view);
        }


    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        wall.setNode(view);
        addEntity(wall, view);
        
    }

    @Override
    public void onLoad(Boulder boulder){
        ImageView view = new ImageView(boulderImage);
        boulder.setNode(view);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Enemy enemy){
        ImageView view = new ImageView(enemyImage);
        enemy.setNode(view);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Treasure treasure){
        ImageView view = new ImageView(treasureImage);
        treasure.setNode(view);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Switch floorswitch){
        ImageView view = new ImageView(switchImage);
        floorswitch.setNode(view);
        addEntity(floorswitch, view);
    }

    @Override
    public void onLoad(Portal portal){
        ImageView view = new ImageView(portalImage);
        portal.setNode(view);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Potion potion){
        ImageView view = new ImageView(potionImage);
        potion.setNode(view);
        addEntity(potion, view);

    }

    @Override
    public void onLoad(Sword sword){
        ImageView view = new ImageView(swordImage);
        sword.setNode(view);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Door door){
        ImageView view = null;
        if(door.getId() == 0){
            view = new ImageView(closeddoorImage);
        }else if(door.getId() == 1){
            view = new ImageView(closeddoorredImage);
        }else if(door.getId() == 2){
            view = new ImageView(closeddoorblueImage);
        }
        door.setNode(view);
        addEntity(door, view);
    }

    @Override
    public void onLoad(Key key){
        ImageView view = null;
        if(key.getId() == 0){
            view = new ImageView(keyImage);
        }else if(key.getId() == 1){
            view = new ImageView(keyredImage);
        }else if(key.getId() == 2){
            view = new ImageView(keyblueImage);
        }
        key.setNode(view);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Exit exit){
        ImageView view = new ImageView(exitImage);
        exit.setNode(view);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(BlackHole hole){
        ImageView view = new ImageView(blackholeImage);
        hole.setNode(view);
        addEntity(hole, view);
    }

    @Override
    public void onLoad(Bomb bomb){
        ImageView view = new ImageView(bombunlitImage);
        bomb.setNode(view);
        addEntity(bomb, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
        
    }



    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()!= 0) {
                    node.setVisible(true);
                    GridPane.setColumnIndex(node, newValue.intValue());
                }
                else {
                    node.setVisible(false);
                }
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()!= 0) {
                    node.setVisible(true);
                    GridPane.setRowIndex(node, newValue.intValue());
                }
                else {
                    node.setVisible(false);
                }
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }


}
