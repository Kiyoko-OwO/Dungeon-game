package unsw.dungeon.frontend;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import javafx.scene.layout.Pane;
import unsw.dungeon.screens.*;

public class StartController {

	@FXML
	private Button start;
	private Button infor;
	private Button exit;

	@FXML
	private Pane back;

	private InformationScreen inforScreen;

	private ModelScreen modelScreen;

	@FXML
	void handleStartButton(ActionEvent event) throws IOException {
		modelScreen.start();
	}
	@FXML
	void handleInforButton(ActionEvent event){
		inforScreen.start();
	}

	@FXML
	void handleExitButton(ActionEvent event){
		Platform.exit();
	}
	
	@FXML 
	public void initialize() {
		Image ground = new Image((new File("images/dungeon.png")).toURI().toString());
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background2 = new Background(new BackgroundImage(ground,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		back.setBackground(background2);
		
	}
	
	public void setInformationScreen(InformationScreen dungeonScreen) {
		this.inforScreen = dungeonScreen;
	}

	public void setModelScreen(ModelScreen dungeonScreen) {
		this.modelScreen = dungeonScreen;
	}
}
