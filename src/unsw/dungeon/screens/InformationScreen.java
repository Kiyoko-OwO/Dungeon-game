package unsw.dungeon.screens;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.dungeon.frontend.*;

public class InformationScreen {
	
	
    private Stage stage;
    private String title;
    private InformationController controller;
    private Scene scene;
    
    public InformationScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Welcome to Dungeon";

        controller = new InformationController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("information.fxml"));
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
    
    public InformationController getController() {
    	return controller;
    }
}
