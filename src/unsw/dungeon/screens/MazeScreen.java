package unsw.dungeon.screens;

import java.io.IOException;

import javafx.stage.Stage;

public class MazeScreen extends Screen {

	public MazeScreen(Stage stage, String filename) throws IOException {
        super(stage, filename, "Getting to an exit");
    }
}
