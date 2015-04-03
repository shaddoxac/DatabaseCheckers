package GUI;

import game.Board;
import game.Game;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

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
	

	private ArrayList<Point> legalPositions;
	private ImageView boardSprite, whiteSprite, blackSprite, whiteKingSprite, blackKingSprite;
	private Game game;
    private Board board;
    private int turnCount=0;
    private Button selectedButton;
    private ImageView selectionBox;
	
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
        int tempBoard=board.whitePos;
        for (int counter=1; counter<33; counter++) {
            if ((tempBoard & 1)!=0) {
                addChecker(counter, image);
            }
        }
    }

    private void addChecker(int location, ImageView image) {
        Button button = (Button) getButtonName(location);
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
    	 b1.setOnAction((event) ->{
         	onAction(b1);
         });
    	 b2.setOnAction((event) ->{
         	onAction(b2);
         }); 
    	 b3.setOnAction((event) ->{
         	onAction(b3);
         });
    	 b4.setOnAction((event) ->{
          	onAction(b4);
          });
    	 b5.setOnAction((event) ->{
          	onAction(b5);
          });
    	 b6.setOnAction((event) ->{
          	onAction(b6);
          });
    	 b7.setOnAction((event) ->{
          	onAction(b7);
          });
    	 b8.setOnAction((event) ->{
          	onAction(b8);
          });
    	 b9.setOnAction((event) ->{
          	onAction(b9);
          });
    	 b10.setOnAction((event) ->{
          	onAction(b10);
          });
    	 b11.setOnAction((event) ->{
          	onAction(b11);
          });
    	 b12.setOnAction((event) ->{
          	onAction(b12);
          });
    	 b13.setOnAction((event) ->{
          	onAction(b13);
          });
    	 b14.setOnAction((event) ->{
          	onAction(b14);
          });
    	 b15.setOnAction((event) ->{
          	onAction(b15);
          });
    	 b16.setOnAction((event) ->{
          	onAction(b16);
          });
    	 b17.setOnAction((event) ->{
          	onAction(b17);
          });
    	 b18.setOnAction((event) ->{
          	onAction(b18);
          });
    	 b19.setOnAction((event) ->{
          	onAction(b19);
          });
    	 b20.setOnAction((event) ->{
          	onAction(b20);
          });
    	 b21.setOnAction((event) ->{
          	onAction(b21);
          });
    	 b22.setOnAction((event) ->{
          	onAction(b22);
          });
    	 b23.setOnAction((event) ->{
          	onAction(b23);
          });
    	 b24.setOnAction((event) ->{
          	onAction(b24);
          });
    	 b25.setOnAction((event) ->{
          	onAction(b25);
          });
    	 b26.setOnAction((event) ->{
          	onAction(b26);
          });
    	 b27.setOnAction((event) ->{
          	onAction(b27);
          });
    	 b28.setOnAction((event) ->{
          	onAction(b28);
          });
    	 b29.setOnAction((event) ->{
          	onAction(b29);
          });
    	 b30.setOnAction((event) ->{
          	onAction(b30);
          });
    	 b31.setOnAction((event) ->{
          	onAction(b31);
          });
    	 b32.setOnAction((event) ->{
          	onAction(b32);
          });
    }

    private Object getButtonName(int num) {
        StringBuilder sb=new StringBuilder();
        sb.append("b");
        sb.append(num);
        try {
            return getClass().getDeclaredField(sb.toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}