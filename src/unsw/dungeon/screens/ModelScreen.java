package unsw.dungeon.screens;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import unsw.dungeon.frontend.*;

public class ModelScreen {

    private Stage stage;
    private String title;
    private ModelController controller;
    private Scene scene;

    public ModelScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Welcome to Dungeon";

        controller = new ModelController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("model.fxml"));
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
    
    public ModelController getController() {
    	return controller;
    }

    public void setModelController() throws IOException {
        DungeonScreen dungeonScreen = getNewDungeonScreen();
        MazeScreen mazeScreen = getNewMazeScreen();
        BoulderScreen boulderScreen = getNewBoulderScreen();
        LevelFourScreen level4Screen = getNewlevel4Screen();
        LevelFiveScreen level5Screen = getNewlevel5Screen();
        LevelSixScreen level6Screen = getNewlevel6Screen();
        LevelSevenScreen level7Screen = getNewlevel7Screen();
        LevelEightScreen level8Screen = getNewlevel8Screen();

        this.controller.setDungeonScreen(dungeonScreen);
        this.controller.setMazeScreen(mazeScreen);
        this.controller.setBoulderScreen(boulderScreen);
        this.controller.setLevel4Screen(level4Screen);
        this.controller.setLevel5Screen(level5Screen);
        this.controller.setLevel6Screen(level6Screen);
        this.controller.setLevel7Screen(level7Screen);
        this.controller.setLevel8Screen(level8Screen);

        mazeScreen.getController().setModelScreen(this);
        dungeonScreen.getController().setModelScreen(this);
        boulderScreen.getController().setModelScreen(this);
        level4Screen.getController().setModelScreen(this);
        level5Screen.getController().setModelScreen(this);
        level6Screen.getController().setModelScreen(this);
        level7Screen.getController().setModelScreen(this);
        level8Screen.getController().setModelScreen(this);
    } 

    public DungeonScreen getNewDungeonScreen() throws IOException {
        DungeonScreen dungeonScreen = new DungeonScreen(this.stage, "advanced.json");
        return dungeonScreen;
    }

    public MazeScreen getNewMazeScreen() throws IOException {
        MazeScreen mazeScreen = new MazeScreen(this.stage, "maze.json");
        return mazeScreen;
    }

    public BoulderScreen getNewBoulderScreen() throws IOException {
        BoulderScreen boulderScreen = new BoulderScreen(this.stage, "boulders.json");
        return boulderScreen;
    }

    public LevelFourScreen getNewlevel4Screen() throws IOException {
        LevelFourScreen level4Screen = new LevelFourScreen(this.stage, "basic.json");
        return level4Screen;
    }

    public LevelFiveScreen getNewlevel5Screen() throws IOException {
        LevelFiveScreen level5Screen = new LevelFiveScreen(this.stage, "level5.json");
        return level5Screen;
    }

    public LevelSixScreen getNewlevel6Screen() throws IOException {
        LevelSixScreen level6Screen = new LevelSixScreen(this.stage, "level6.json");
        return level6Screen;
    }

    public LevelSevenScreen getNewlevel7Screen() throws IOException {
        LevelSevenScreen level7Screen = new LevelSevenScreen(this.stage, "level7.json");
        return level7Screen;
    }

    public LevelEightScreen getNewlevel8Screen() throws IOException {
        LevelEightScreen level8Screen = new LevelEightScreen(this.stage, "medium.json");
        return level8Screen;
    }
}
