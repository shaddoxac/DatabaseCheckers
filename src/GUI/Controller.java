package GUI;

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
	ChoiceBox<String> difficultyBox;
	
	@FXML
	ChoiceBox<String> boardStyleBox;
	
	
	@FXML
	private void initialize() {
        setUpBoxes();
	}



    private void setUpBoxes() {
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium");
        boardStyleBox.getItems().addAll("Style1", "Style2", "Style3");
        boardStyleBox.setValue("Style1");
    }
}
