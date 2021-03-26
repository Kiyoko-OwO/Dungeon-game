package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import unsw.dungeon.screens.*;

public class DungeonApplication extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        StartScreen startScreen = new StartScreen(primaryStage);
        
        
        InformationScreen informationScreen = new InformationScreen(primaryStage);
        ModelScreen modelScreen = new ModelScreen(primaryStage);
        
        

        startScreen.getController().setInformationScreen(informationScreen);
        startScreen.getController().setModelScreen(modelScreen);
        
        modelScreen.getController().setStartScreen(startScreen);
        modelScreen.setModelController();
        

        informationScreen.getController().setStartScreen(startScreen);
        primaryStage.setTitle("Dungeon");
        startScreen.start();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
