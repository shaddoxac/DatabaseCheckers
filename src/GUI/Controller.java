package GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Controller {
	
	@FXML
	Button newGame;
	
	@FXML
	TextField numMoves;
	
	@FXML
	ChoiceBox<String> difficulty;
	
	@FXML
	ChoiceBox<String> boardStyle;
	
	
	@FXML
	private void initialize() {
		difficulty = new ChoiceBox<String>(FXCollections.observableArrayList(
			"Easy", "Medium", "Hard")
		);
		boardStyle = new ChoiceBox<String>(FXCollections.observableArrayList(
			"Style1", "Style2", "Style3")
		);
		
	}
}
