package GUI;

import game.Game;
import game.PieceType;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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

public class Controller {
	
	@FXML
	Pane canvas;
	
	@FXML
	Pane whiteGraveyard;
	
	@FXML
	Pane blackGraveyard;
	
	@FXML
	ImageView board = new ImageView();
	
	//Each clickable (black) tile on the board.  Proceeds from top left to bottom right.
	@FXML Button b1; @FXML Button b2; @FXML Button b3; @FXML Button b4; @FXML Button b5; @FXML Button b6; @FXML Button b7; @FXML Button b8; @FXML Button b9; @FXML Button b10; @FXML Button b11; @FXML Button b12; @FXML Button b13; @FXML Button b14; @FXML Button b15; @FXML Button b16; @FXML Button b17; @FXML Button b18; @FXML Button b19; @FXML Button b20; @FXML Button b21; @FXML Button b22; @FXML Button b23; @FXML Button b24; @FXML Button b25; @FXML Button b26; @FXML Button b27; @FXML Button b28; @FXML Button b29; @FXML Button b30; @FXML Button b31; @FXML Button b32;
	
	Button selectedButton;
	
	ImageView selectionBox;
	
	@FXML
	Button newGame;
	
	@FXML
	TextField numMoves;
	
	@FXML
	TextField turnIndicator;
	
	@FXML
	ChoiceBox<String> difficultyBox;
	
	@FXML
	ChoiceBox<String> boardStyleBox;
	
	
	
	
	ArrayList<Point> legalPositions;
	Image boardSprite, whiteSprite, blackSprite, whiteKingSprite, blackKingSprite;
	
	
	Game game;
	
	
	@FXML
	private void initialize() {
		//createLegalPositions();
        turnIndicator.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
        turnIndicator.setStyle("-fx-text-inner-color: black;");
        setUpBoxes();
        //changeBoard();
        
        Image image = new Image("/img/Wooden Board/Selection_Highlight.png");
		selectionBox = new ImageView(image);
		selectionBox.setVisible(false);
		canvas.getChildren().add(selectionBox);
        
        setUpButtons();
        
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
	
	
	private void highlightSelected(Button b) {
		selectedButton = (b.equals(selectedButton)) ? null : b;
		if (selectedButton != null) {
			selectionBox.setLayoutX(b.getLayoutX());
			selectionBox.setLayoutY(b.getLayoutY());
			selectionBox.setVisible(true);
		}
		else {selectionBox.setVisible(false);;}
	}
	
	private void createLegalPositions() {
		int rowNum = 1;
		for (int y=32; y<512; y+=64){
			for (int x=32; x<512; x+=128) {
				if (rowNum % 2 == 1) {legalPositions.add(new Point(x+64, y));}
				else {legalPositions.add(new Point(x,y));}
			}
			rowNum++;
		}
	}
	
    private void setUpBoxes() {
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium");
        boardStyleBox.getItems().addAll("Style 1", "Style 2", "Style 3"); //TODO: make different board styles
        boardStyleBox.setValue("Style 1");
    }
    
    private void setUpButtons() {
    	 b1.setOnAction((event) ->{
         	highlightSelected(b1);
         });
    	 b2.setOnAction((event) ->{
         	highlightSelected(b2);
         }); 
    	 b3.setOnAction((event) ->{
         	highlightSelected(b3);
         });
    	 b4.setOnAction((event) ->{
          	highlightSelected(b4);
          });
    	 b5.setOnAction((event) ->{
          	highlightSelected(b5);
          });
    	 b6.setOnAction((event) ->{
          	highlightSelected(b6);
          });
    	 b7.setOnAction((event) ->{
          	highlightSelected(b7);
          });
    	 b8.setOnAction((event) ->{
          	highlightSelected(b8);
          });
    	 b9.setOnAction((event) ->{
          	highlightSelected(b9);
          });
    	 b10.setOnAction((event) ->{
          	highlightSelected(b10);
          });
    	 b11.setOnAction((event) ->{
          	highlightSelected(b11);
          });
    	 b12.setOnAction((event) ->{
          	highlightSelected(b12);
          });
    	 b13.setOnAction((event) ->{
          	highlightSelected(b13);
          });
    	 b14.setOnAction((event) ->{
          	highlightSelected(b14);
          });
    	 b15.setOnAction((event) ->{
          	highlightSelected(b15);
          });
    	 b16.setOnAction((event) ->{
          	highlightSelected(b16);
          });
    	 b17.setOnAction((event) ->{
          	highlightSelected(b17);
          });
    	 b18.setOnAction((event) ->{
          	highlightSelected(b18);
          });
    	 b19.setOnAction((event) ->{
          	highlightSelected(b19);
          });
    	 b20.setOnAction((event) ->{
          	highlightSelected(b20);
          });
    	 b21.setOnAction((event) ->{
          	highlightSelected(b21);
          });
    	 b22.setOnAction((event) ->{
          	highlightSelected(b22);
          });
    	 b23.setOnAction((event) ->{
          	highlightSelected(b23);
          });
    	 b24.setOnAction((event) ->{
          	highlightSelected(b24);
          });
    	 b25.setOnAction((event) ->{
          	highlightSelected(b25);
          });
    	 b26.setOnAction((event) ->{
          	highlightSelected(b26);
          });
    	 b27.setOnAction((event) ->{
          	highlightSelected(b27);
          });
    	 b28.setOnAction((event) ->{
          	highlightSelected(b28);
          });
    	 b29.setOnAction((event) ->{
          	highlightSelected(b29);
          });
    	 b30.setOnAction((event) ->{
          	highlightSelected(b30);
          });
    	 b31.setOnAction((event) ->{
          	highlightSelected(b31);
          });
    	 b32.setOnAction((event) ->{
          	highlightSelected(b32);
          });
    }
    
    /*private void drawPieces() {
    	int blackPieces = game.getBitBoard(PieceType.BLACK);
    	drawGroup(blackPieces);
    	int whitePieces = game.getBitBoard(PieceType.WHITE);
    	drawGroup(whitePieces);
    	int blackKings = game.getBitBoard(PieceType.BLACKKING);
    	drawGroup(blackKings);
    	int whiteKings = game.getBitBoard(PieceType.WHITEKING);
    	drawGroup(whiteKings);	
    }
    
    private void drawGroup(int bitBoard, PieceType ) {
        int tempNum=bitBoard;
        int index;
        int value;
        while (0 < tempNum) {
            value=tempNum & 1;
            if (value = 1) {
            tempNum=tempNum >> 1;
            }
        }
    }
    
    @FXML
    private void changeBoard() {
    	String boardStyle = boardStyleBox.getValue();
    	switch (boardStyle) {
    	case "Style 1":

    		File boardFile = new File("/img/Wooden Board/Game_Board.png")
    		boardSprite = ImageIO.read(boardFile); //TODO: make this work
    		whiteSprite = new Image();
    		blackSprite = new Image();
    		whiteKingSprite = new Image();
    		blackKingSprite = new Image();
    		break;
    		
    	case "Style 2":
    		boardSprite = new Image(); 
    		whiteSprite = new Image();
    		blackSprite = new Image();
    		whiteKingSprite = new Image();
    		blackKingSprite = new Image();
    		break;
    		
    	case "Style 3":
    		boardSprite = new Image();
    		whiteSprite = new Image();
    		blackSprite = new Image();
    		whiteKingSprite = new Image();
    		blackKingSprite = new Image();
    		break;
    	}
    	board.setImage(boardSprite);
    	drawPieces();
    }*/

    private void exampleAddButton() {/*
        Card tempCard=deck.getCard(row*width+column);
        cardGrid[row][column]=tempCard;
        Image image=getImageFromCard(tempCard);
        ImageView imgView = new ImageView(image);
        double wide = imageGrid.getMinWidth() / width;
        double height = imageGrid.getMinHeight() / width;
        imgView.setFitHeight(height);
        imgView.setFitWidth(wide);


        final Button item = new Button();
        item.setGraphic(imgView);
        item.setMinSize(wide, height);
        item.setMaxSize(wide, height);
        item.setOnAction((e) -> {
            selected = new Point2D(row, column);
        });
        item.setOnMouseClicked((MouseEvent e) ->{
            if (e.getClickCount() >= 2){
                if (isEditable()) {
                    Node graphic = item.getGraphic();
                    boolean isVisible = !graphic.isVisible();
                    graphic.setVisible(isVisible);
                }
            }
        });

        imageGrid.add(item, column, row);
    */}
}
