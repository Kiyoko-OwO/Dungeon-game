package unsw.dungeon.screens;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import unsw.dungeon.*;
import unsw.dungeon.DungeonControllerLoader;
/**
 * a sceen class to create the different dungeon sceen
 */
public class Screen {
    private Stage stage;
    private String title;
    private DungeonController controller;
    
    private Scene scene;
    private String filename;
    private String goalName;

    public Screen(Stage stage, String filename, String goalName) throws IOException {
    	this.stage = stage;
        title ="Dungeon";
        this.filename = filename;
    	
    	DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(filename);

        controller = dungeonLoader.loadController();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
        
        
        int row = controller.getDungeon().getWidth();
        int column = controller.getDungeon().getHeight();
        
        this.goalName = goalName;
        Label goal = new Label(goalName);
    	controller.getGridPane().add(goal, 0, row+1, column , row+1);
    	goal.setTextFill(Color.web("#FFFFFF"));
        goal.setFont(new Font("Arial Black", 16));
        
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        this.controller.start();
    }
    
    public DungeonController getController() {
    	return controller;
    }
}