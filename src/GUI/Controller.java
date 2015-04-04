package GUI;

import game.Board;
import game.Game;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Controller {
	
	@FXML
	Pane canvas;
	
	@FXML
	Pane whiteGraveyard;
	
	@FXML
	Pane blackGraveyard;
	
	@FXML
	ImageView boardImage = new ImageView();
	
	//Each clickable (black) tile on the board.  Proceeds from top left to bottom right.
	@FXML Button b1; @FXML Button b2; @FXML Button b3; @FXML Button b4; @FXML Button b5; @FXML Button b6; @FXML Button b7; @FXML Button b8; @FXML Button b9; @FXML Button b10; @FXML Button b11; @FXML Button b12; @FXML Button b13; @FXML Button b14; @FXML Button b15; @FXML Button b16; @FXML Button b17; @FXML Button b18; @FXML Button b19; @FXML Button b20; @FXML Button b21; @FXML Button b22; @FXML Button b23; @FXML Button b24; @FXML Button b25; @FXML Button b26; @FXML Button b27; @FXML Button b28; @FXML Button b29; @FXML Button b30; @FXML Button b31; @FXML Button b32;
	
	@FXML
	Button newGame;
	
	@FXML
    Label numMoves;
	
	@FXML
	TextField turnIndicator;
	
	@FXML
	ChoiceBox<String> difficultyBox;
	
	@FXML
	ChoiceBox<String> boardStyleBox;

	private ImageView boardSprite, whiteSprite, blackSprite, whiteKingSprite, blackKingSprite;
	private Game game;
    private Board board;
    private int turnCount=0;
    private Button selectedButton;
    private ImageView selectionBox;
    private Field field;
    private HashMap<Integer,Button> buttonMap =new HashMap<Integer,Button>();
    private HashMap<Button,Integer> locationMap=new HashMap<Button,Integer>();

    private final int numSquares = 32;
	
	@FXML
	private void initialize() {
        turnIndicator.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
        turnIndicator.setStyle("-fx-text-inner-color: black;");
        setUpBoxes();
        createSelectionBox();
        setUpButtons();
        newGame();

    }

    @FXML
    private void newGame() {
        selectionBox.setVisible(false);
        game=new Game();
        board=game.board;
        setCheckerLocations();
    }

    private void commitTurn() {

        //game.commitMove(move);
    }

    private void onAction(Button b) {
        setHighlight(b);
        int location=numSquares-locationMap.get(b);
    }

	private void switchTurns() {
		String currentTurn = turnIndicator.getText();
		boolean isPlayerTurn = currentTurn.equals("Your turn");
		String newTurn = isPlayerTurn ? "CPU turn" : "Your turn";
		String newColor = isPlayerTurn ? "white" : "black";
		turnIndicator.setText(newTurn);
		turnIndicator.setStyle("-fx-text-inner-color: " + newColor + ";");
		game.changeTurn();
	}

    private void setCheckerLocations() {
        setLocations(board.whitePos, whiteSprite);
        setLocations(board.blackPos, blackSprite);
        setLocations(board.whiteKingPos, whiteKingSprite);
        setLocations(board.blackKingPos, blackKingSprite);
    }

    private void setLocations(int type, ImageView image) {
        int tempBoard=type;
        for (int counter=1; counter<33; counter++) {
            if ((tempBoard & 1)!=0) {
                addChecker(33-counter, image);
            }
            tempBoard=tempBoard>>1;
        }
    }

    private void addChecker(int location, ImageView image) {
        Button button = getButton(location);
        if (button!=null) {
            button.setGraphic(image);
            System.out.println("here");
        }
    }

	
	private void setHighlight(Button b) {
		selectedButton = (b.equals(selectedButton)) ? null : b;
		if (selectedButton != null) {
			selectionBox.setLayoutX(b.getLayoutX());
			selectionBox.setLayoutY(b.getLayoutY());
			selectionBox.setVisible(true);
		}
		else {selectionBox.setVisible(false);}
	}

    private void createSelectionBox() {
        Image selectionImage = new Image("/img/Wooden Board/Selection_Highlight.png");
        selectionBox = new ImageView(selectionImage);
        selectionBox.setVisible(false);
        canvas.getChildren().add(selectionBox);
    }

    private void setUpBoxes() {
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium");
        boardStyleBox.getItems().addAll("Style 1", "Style 2", "Style 3"); //TODO: make different board styles
        boardStyleBox.setValue("Style 1");
    }
    
    private void setUpButtons() {
        buttonMap.put(1,b1);
        buttonMap.put(2,b2);
        buttonMap.put(3,b3);
        buttonMap.put(4,b4);
        buttonMap.put(5,b5);
        buttonMap.put(6,b6);
        buttonMap.put(7,b7);
        buttonMap.put(8,b8);
        buttonMap.put(9,b9);
        buttonMap.put(10,b10);
        buttonMap.put(11,b11);
        buttonMap.put(12,b12);
        buttonMap.put(13,b13);
        buttonMap.put(14,b14);
        buttonMap.put(15,b15);
        buttonMap.put(16,b16);
        buttonMap.put(17,b17);
        buttonMap.put(18,b18);
        buttonMap.put(19,b19);
        buttonMap.put(20,b20);
        buttonMap.put(21,b21);
        buttonMap.put(22,b22);
        buttonMap.put(23,b23);
        buttonMap.put(24,b24);
        buttonMap.put(25,b25);
        buttonMap.put(26,b26);
        buttonMap.put(27,b27);
        buttonMap.put(28,b28);
        buttonMap.put(29,b29);
        buttonMap.put(30,b30);
        buttonMap.put(31,b31);
        buttonMap.put(32,b32);
        for (int idx=1; idx<= numSquares; idx++) {
            Button button= buttonMap.get(idx);
            button.setOnAction((event) -> {
                onAction(button);
            });
        }
    }

    private Button getButton(int num) {
        return buttonMap.get(num);
    }
}