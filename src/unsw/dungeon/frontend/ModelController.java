package unsw.dungeon.frontend;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import unsw.dungeon.screens.*;

public class ModelController {

	@FXML
	private Pane back;

	private MazeScreen mazeScreen;

	private DungeonScreen dungeonScreen;

	private BoulderScreen boulderScreen;

	private LevelFiveScreen level5Screen;

	private LevelSixScreen level6Screen;

	private LevelSevenScreen level7Screen;

	private LevelEightScreen level8Screen;

	private LevelFourScreen level4Screen;

	private StartScreen startScreen;

	private ModelScreen modelScreen;

	public ModelController(ModelScreen modelScreen) {
		this.modelScreen = modelScreen;
	}
	// to make the level button to restart different dungeon game
	@FXML
	void handleLevel1(ActionEvent event) throws IOException {
		this.mazeScreen = this.modelScreen.getNewMazeScreen();
		this.mazeScreen.getController().setModelScreen(this.modelScreen);
		mazeScreen.start();
	}

	@FXML
	void handleLevel2(ActionEvent event) throws IOException {
		this.boulderScreen = this.modelScreen.getNewBoulderScreen();
		this.boulderScreen.getController().setModelScreen(this.modelScreen);
		boulderScreen.start();
	}

	@FXML
	void handleLevel3(ActionEvent event) throws IOException {
		this.dungeonScreen = this.modelScreen.getNewDungeonScreen();
		this.dungeonScreen.getController().setModelScreen(this.modelScreen);
		dungeonScreen.start();
	}

	@FXML
	void handleLevel4(ActionEvent event) throws IOException {
		this.level4Screen = this.modelScreen.getNewlevel4Screen();
		this.level4Screen.getController().setModelScreen(this.modelScreen);
		level4Screen.start();
	}

	@FXML
	void handleLevel5(ActionEvent event) throws IOException {
		this.level5Screen = this.modelScreen.getNewlevel5Screen();
		this.level5Screen.getController().setModelScreen(this.modelScreen);
		level5Screen.start();
	}

	@FXML
	void handleLevel6(ActionEvent event) throws IOException {
		this.level6Screen = this.modelScreen.getNewlevel6Screen();
		this.level6Screen.getController().setModelScreen(this.modelScreen);
		level6Screen.start();
	}

	@FXML
	void handleLevel7(ActionEvent event) throws IOException {
		this.level7Screen = this.modelScreen.getNewlevel7Screen();
		this.level7Screen.getController().setModelScreen(this.modelScreen);
		level7Screen.start();
	}

	@FXML
	void handleLevel8(ActionEvent event) throws IOException {
		this.level8Screen = this.modelScreen.getNewlevel8Screen();
		this.level8Screen.getController().setModelScreen(this.modelScreen);
		level8Screen.start();
	}

	// back to the start screen
	@FXML
	void handleBack(ActionEvent event) throws IOException {
		startScreen.start();
	}

	@FXML
	public void initialize() {
        Image ground = new Image((new File("images/modelBack.png")).toURI().toString());
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background2 = new Background(new BackgroundImage(ground,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
        back.setBackground(background2);
        

        
    }
	// set the different dungeon screen
    public void setMazeScreen(MazeScreen dungeonScreen) {
		this.mazeScreen = dungeonScreen;
	}
	
	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
	}
    
    public void setBoulderScreen(BoulderScreen dungeonScreen) {
		this.boulderScreen = dungeonScreen;
	}
	
	public void setLevel4Screen(LevelFourScreen dungeonScreen) {
		this.level4Screen = dungeonScreen;
    }
    public void setLevel5Screen(LevelFiveScreen dungeonScreen) {
		this.level5Screen = dungeonScreen;
	}
	
	public void setLevel6Screen(LevelSixScreen dungeonScreen) {
		this.level6Screen = dungeonScreen;
    }
    public void setLevel7Screen(LevelSevenScreen dungeonScreen) {
		this.level7Screen = dungeonScreen;
	}
	
	public void setLevel8Screen(LevelEightScreen dungeonScreen) {
		this.level8Screen = dungeonScreen;
    }
    
    public void setStartScreen(StartScreen informationScreen) {
		this.startScreen = informationScreen;
	}
}
