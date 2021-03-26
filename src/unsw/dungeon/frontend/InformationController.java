package unsw.dungeon.frontend;

import java.io.File;

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

public class InformationController {

	@FXML
	private Button go;

	private StartScreen startScreen;

	@FXML
	private Pane back;

	@FXML
	public void initialize() {
		Image ground = new Image((new File("images/information.png")).toURI().toString());
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background2 = new Background(new BackgroundImage(ground,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.CENTER,
	            bSize));
		back.setBackground(background2);
        
	}
	@FXML
	void handleGo(ActionEvent event) {
		startScreen.start();
	}
	
	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}
	
}
