package unsw.dungeon.screens;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import unsw.dungeon.frontend.*;

public class StartScreen {

    private Stage stage;
    private String title;
    private StartController controller;
    private Scene scene;

    public StartScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Welcome to Dungeon";

        controller = new StartController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        loader.setController(controller);

        // load into a Parent node called root

		
        Parent root = loader.load();
        scene = new Scene(root);
        
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    public StartController getController() {
    	return controller;
    }
}
