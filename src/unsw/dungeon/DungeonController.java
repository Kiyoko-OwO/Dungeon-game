package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import unsw.dungeon.attackmode.*;

import javafx.application.Platform;
import javafx.util.Duration;
import unsw.dungeon.entity.*;
import unsw.dungeon.screens.*;
import java.io.File;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    @FXML
    private Pane main;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private Timeline timeline;

    private ModelScreen modelScreen;

    Label status;
    Label bombtime;
    Label swordtime;
    Label potiontime;
    Label keytime;
    Label keytime1;
    Label keytime2;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.player = dungeon.getPlayer();
        this.dungeon = this.player.getDungeon();

        this.initialEntities = new ArrayList<>(initialEntities);

        // make a timeline to check the goal and move the enemy
        this.timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (dungeon.getGoal().checkGoal()) {
                    timeline.stop();
                    winGame();
                }
                boolean changePath = false;
                if (dungeon.getPlayer().checkStatus()) {
                    changePath = true;
                }

                for (Entity e : dungeon.getEntity()) {
                    if (e == null)
                        break;
                    if (e instanceof Enemy) {
                        Enemy i = (Enemy) e;
                        if (changePath) {
                            i.moveTo(dungeon.getPlayer().getX(), dungeon.getPlayer().getY(), i.getX(), i.getY());
                        }
                        i.update(dungeon.getPlayer());
                        i.autoMove();
                    }
                    if (e instanceof Exit && player.getX() == e.getX() && player.getY() == e.getY()) {
                        if (! dungeon.getGoal().checkGoal()) {
                            notFinishGame();
                        }
                    }
                }
                dungeon.getPlayer().checkStatus();
                checkBag();

            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void initialize() {
        Image ground1 = new Image((new File("images/modelBack.png")).toURI().toString());
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background2 = new Background(new BackgroundImage(ground1,
	            BackgroundRepeat.REPEAT,
	            BackgroundRepeat.REPEAT,
	            BackgroundPosition.DEFAULT,
	            bSize));
        main.setBackground(background2);


        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        int column = 0;
        int row = 0;
        // Add the ground first so it is below all other entities
        
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
                column = x;
                row = y;
            }
        }

        for (ImageView entity : initialEntities) {
            squares.getChildren().add(entity);
        }
        Image player = new Image((new File("images/human_new.png")).toURI().toString());
        squares.add(new ImageView(player), column+1, 1);
        status = new Label("Normal");
        squares.add(status, column + 2, 1, column + 3, 1);
        status.setTextFill(Color.web("white"));
        
        Image bomb = new Image((new File("images/bomb_unlit.png")).toURI().toString());
        squares.add(new ImageView(bomb), column+1, 3);
        bombtime = new Label("0");
        squares.add(bombtime, column + 2, 3);
        bombtime.setTextFill(Color.web("white"));
        
        
        Image sword = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        squares.add(new ImageView(sword), column+1, 5);
        swordtime = new Label("0");
        squares.add(swordtime, column+2, 5);
        swordtime.setTextFill(Color.web("white"));
        
        Image yellowkey = new Image((new File("images/key.png")).toURI().toString());
        squares.add(new ImageView(yellowkey), column+1, 7);
        keytime = new Label("0");
        squares.add(keytime, column+2, 7);
        keytime.setTextFill(Color.web("white"));

        Image redkey = new Image((new File("images/redkey.png")).toURI().toString());
        squares.add(new ImageView(redkey), column+1, 9);
        keytime1 = new Label("0");
        squares.add(keytime1, column+2, 9);
        keytime1.setTextFill(Color.web("white"));

        Image bluekey = new Image((new File("images/bluekey.png")).toURI().toString());
        squares.add(new ImageView(bluekey), column+1, 11);
        keytime2 = new Label("0");
        squares.add(keytime2, column+2, 11);
        keytime2.setTextFill(Color.web("white"));

        Button exit = new Button("BACK");
        
        squares.add(exit, column+1, 20, column+1, 20);
        exit.setStyle("-fx-background-color: Transparent");
        exit.setTextFill(Color.web("white"));
        exit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                timeline.stop();
                modelScreen.start();
            }
        }); 
    }


    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            if (! moveCheck(0, -1)) {
                player.moveUp();
            }
            
            break;

        case DOWN:
            if (! moveCheck(0, 1)) {
                player.moveDown();
            }
            break;

        case LEFT:
            if (! moveCheck(-1, 0)) {
                player.moveLeft();
            }
            break;

        case RIGHT:
            if (! moveCheck(1, 0)) {
                player.moveRight();
            }
            break;
   

        case J: 
            // to pick up the bomb and make bomb explode and change the picktue
            Player player = dungeon.getPlayer();
            int x = player.getX();
            int y = player.getY();
            if(player.getBag().getBomb() != null) {                          
                player.placebomb();
                Image ground = new Image((new File("images/bomb_unlit.png")).toURI().toString());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        squares.add(new ImageView(ground), x+1, y);
                    }
                });
                for(Entity i:dungeon.getEntity()) {
                	if(i instanceof Bomb && i.getBombStatus() == 1 ) {
                        Timer time = new Timer();
                        TimerTask task = new TimerTask() {
                            private int count = 4;
                            ArrayList<Node> node = new ArrayList<Node>();
                            public void run() {
                                if(count == 4) {
                                    Image ground = new Image((new File("images/bomb_lit_1.png")).toURI().toString());
                                     for(Entity i:dungeon.getEntity()) {
                                       if(i instanceof Bomb & i.getX() == x+1 && i.getY() == y) {
                                        //if(i instanceof Bomb){
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    squares.add(new ImageView(ground), i.getX(), i.getY());
                                                }
                                            });
                                            break;
                                        }
                                    }
                                    count --;
                                }else if(count == 3) {
                                    Image ground = new Image((new File("images/bomb_lit_2.png")).toURI().toString());
                                    for(Entity i:dungeon.getEntity()) {
                                        if(i instanceof Bomb && i.getX() == x+1 && i.getY() == y) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    squares.add(new ImageView(ground), i.getX(), i.getY());
                                                }
                                            });
                                            break;
                                        }
                                    }
                                    count --;
                                }else if(count == 2) {
                                    Image ground = new Image((new File("images/bomb_lit_3.png")).toURI().toString());         
                                    for(Entity i:dungeon.getEntity()) {
                                        if((i.getY() ==y && i.getX() == x) || (i.getY() ==y && i.getX() == x + 2)||(i.getY() ==y+1 && i.getX() == x+1)||(i.getY() ==y-1 && i.getX() == x+1)){;
                                            if((i instanceof Boulder) || (i instanceof Sword) || (i instanceof Enemy) || (i instanceof Key) || (i instanceof Potion) || (i instanceof Player)){
                                                node.add(i.getNode());
                                            }
                                        }
                                        if(i instanceof Bomb && i.getX() == x+1 && i.getY() == y) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    squares.add(new ImageView(ground), i.getX(), i.getY());
                                                }
                                            });
                                            break;
                                        }
                                    }
                                    count --;
                                }else if(count == 1) {
                                    Image ground = new Image((new File("images/bomb_lit_4.png")).toURI().toString());
                                    for(Entity i:dungeon.getEntity()) {
                                        
                                        if(i instanceof Bomb && i.getX() == x+1 && i.getY() == y) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    squares.add(new ImageView(ground), i.getX(), i.getY());
                                                    
                                                }
                                            });
                                            break;
                                        }
                                        
                                    }
                                    count --;
                                }else if(count == 0) {
                                    for (Node n : node) {
                                        n.setVisible(false);
                                    }
                                    
                                    for(Entity i:dungeon.getEntity()) {

                                        
                                    	Node back = null;
                                    	for(Node n : squares.getChildren()) {
                                            if(n instanceof ImageView && squares.getRowIndex(n) == y && squares.getColumnIndex(n) == x+1) {
                                                back = n;
                                                break;
                                            }
                                        }
                                        for(Node n : squares.getChildren()) {
                                            if(n instanceof ImageView && squares.getRowIndex(n) == y && squares.getColumnIndex(n) == x+1) {
                                                if(n != back) {
                                                    n.setVisible(false);
                                                    //break;
                                                }
                                            }
                                        }
                                       
                                        
                                    }
                                    cancel();
                                }

                            }
                        };
                        time.schedule(task, 1000L, 1000L);
                        break;
                	}
                }
            }
            break;
        case G:
            // to throw the sword
            dungeon.getPlayer().throwsword();
            for(Entity i : dungeon.getEntity()){
                if(i instanceof Sword && i.getX() == dungeon.getPlayer().getX()+1 && i.getY() == dungeon.getPlayer().getY()){
                    squares.add(i.getNode(),dungeon.getPlayer().getX()+1, dungeon.getPlayer().getY());
                    break;
                }
            }
            
            
            break;
        default:
            break;
        }
    }

    private boolean moveCheck(int plusx, int plusy) {
        boolean moved = false;
        List<Entity> entities = dungeon.getEntity();
        // check if the player is dead
        if(!player.getStatus()) {
            status.setText("Die");
            bombtime.setText(Integer.toString(0));
            swordtime.setText(Integer.toString(0));
            dieGame();
            return true;
        }
        
        changeDoor(player);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                checkBag();
            }
        });
        // check if there is a entity should remove from the screen
        for(Entity i : entities){
            if(i == null){
                break;
            }
            if(i.getX() == (player.getX() + plusx) && i.getY() == (player.getY() + plusy) && 
                (i instanceof Sword || i instanceof Key || i instanceof Treasure || i instanceof Bomb || i instanceof Potion)){
                Node node = i.getNode();
                if (plusx == 1) {
                    player.moveRight();;
                } else if (plusx == -1) {
                    player.moveLeft();
                } else if (plusy == 1) {
                    player.moveDown();
                } else if (plusy == -1) {
                    player.moveUp();
                }
                moved = true;
                Entity o = null;
                for(Entity i1 : entities){
                    if (i1 == null) break;
                    if(i1.getX() == player.getX() && i1.getY() == player.getY() &&!(i1 instanceof Player)){
                        o = i1;
                        break;
                    }
                }
                if(o == null){
                    squares.getChildren().remove(node);
                }
                return moved;
            }
        }
        return moved;
    }

    public GridPane getGridPane() {
    	return squares;
    }
    
    public Dungeon getDungeon() {
    	return dungeon;
    }

    public void changeDoor(Player player){
        // open the door and change the door picture
        for(Entity i : dungeon.getEntity()){
            if (i == null)  break;
            if((i.getX() == (player.getX() + 1) && i.getY() == player.getY())||
                (i.getX() == (player.getX() - 1) && i.getY() == player.getY()) ||
                (i.getX() == (player.getX()) && (i.getY() == player.getY() + 1))||
                (i.getX() == (player.getX()) && (i.getY() == player.getY() - 1))){
                    if(i instanceof Door){
                        Door door = (Door) i;
                        Node node = door.getNode();
                        
                        if(player.getBag().getKey() != null){
                            if(player.getBag().getKeyId() == door.getId()){
                                if(door.getId() == 0){
                                    Image key = new Image((new File("images/open_door.png")).toURI().toString());
                                    ImageView view = new ImageView(key);
                                	door.setNode(view);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            squares.getChildren().remove(node);
                                            squares.add(new ImageView(key), i.getX(), i.getY());
                                        }
                                    });
                                }else if (door.getId() == 1){
                                    Image key = new Image((new File("images/open_reddoor.png")).toURI().toString());
                                    ImageView view = new ImageView(key);
                                	door.setNode(view);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            squares.getChildren().remove(node);
                                            squares.add(new ImageView(key), i.getX(), i.getY());
                                        }
                                    });
                                }else{
                                    Image key = new Image((new File("images/open_bluedoor.png")).toURI().toString());
                                    ImageView view = new ImageView(key);
                                    door.setNode(view);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            squares.getChildren().remove(node);
                                            squares.add(new ImageView(key), i.getX(), i.getY());
                                        }
                                    });
                                }
                            }
                        }
                    }
                    
                }
        }
    }

    public void setModelScreen(ModelScreen startScreen) {
	 	this.modelScreen = startScreen;
	}

    public void winGame() {
        // if win game, show that
        Label wingame = new Label("         Congratulations!!!!!! You WIN the game          ");
        wingame.setStyle("-fx-background-color: #00FF00");
        wingame.setFont(new Font("Arial", 13));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                squares.add(wingame, dungeon.getWidth()/2 -1,dungeon.getHeight(),dungeon.getWidth()/2 + 1,dungeon.getHeight());
            }
        });
    }

    public void dieGame() {
        // if die, show that
        Label wingame = new Label("                    Game Over!!!!!! You Died                    ");
        wingame.setStyle("-fx-background-color: red");
        wingame.setFont(new Font("Arial", 13));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                squares.add(wingame, dungeon.getWidth()/2-1,dungeon.getHeight(),dungeon.getWidth()/2 + 1,dungeon.getHeight());
                status.setText("Die");
                bombtime.setText(Integer.toString(0));
                swordtime.setText(Integer.toString(0));
            }
        });
    }

    public void notFinishGame() {
        // if go to the exit but not finish all goal, show that
        Label wingame = new Label("You cannot exit, go back and try to finish all goals!");
        wingame.setStyle("-fx-background-color: white");
        wingame.setFont(new Font("Arial", 13));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                squares.add(wingame, dungeon.getWidth()/2-1,dungeon.getHeight(),dungeon.getWidth()/2 + 1,dungeon.getHeight());
            }
        });
    }

    public void checkBag() {
        // check the player status
        status.setText("Normal");
        if(player.getAttackmode() instanceof Armed) {
            status.setText("Sword!");
        }
        if(player.getAttackmode() instanceof Invicible) {
            status.setText("Invincible!");
        }
        if(! player.getStatus()) {
            this.timeline.stop();
            status.setText("Die");
            dieGame();
        }

        // check and change the bag entity
        if(player.getBag().getBomb()!=null){
            bombtime.setText(Integer.toString(1));
        }else{
            bombtime.setText(Integer.toString(0));
        }
                    
        swordtime.setText(Integer.toString(player.getBag().getSwordTime()));
        if(player.getBag().getKey() != null) {
            if(player.getBag().getKey().getId() == 0) {
                keytime.setText(Integer.toString(1));
                keytime1.setText(Integer.toString(0));
                keytime2.setText(Integer.toString(0));
            }else if(player.getBag().getKey().getId() == 1) {
                keytime.setText(Integer.toString(0));
                keytime1.setText(Integer.toString(1));
                keytime2.setText(Integer.toString(0));
            }else if(player.getBag().getKey().getId() == 2) {
                keytime.setText(Integer.toString(0));
                keytime1.setText(Integer.toString(0));
                keytime2.setText(Integer.toString(1));
            }
        } else {
            keytime.setText(Integer.toString(0));
            keytime1.setText(Integer.toString(0));
            keytime2.setText(Integer.toString(0));
        }
    }

    public void start() {
        this.timeline.play();
    }
}

