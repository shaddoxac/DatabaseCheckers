package GUI;

import game.*;
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

import java.util.ArrayList;
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

	private Image whiteSprite, blackSprite, whiteKingSprite, blackKingSprite;
    private ImageView selectionBox, moveBox;
	private Game game;
    private int turnCount=0;
    private Button selectedButton;
    private HashMap<Integer,Button> buttonMap =new HashMap<>();
    private HashMap<Button,Integer> locationMap=new HashMap<>();
    private int selectedChar;
    private boolean gameStarted=false;
    private final int numSquares = 32;
    private int numWhiteDead;
    private int numBlackDead;

    @FXML
	private void initialize() {
        turnIndicator.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
        turnIndicator.setStyle("-fx-text-inner-color: black;");
        setUpBoxes();
        createSprites();
        setUpButtons();
        fillGraveyards();
    }

    @FXML
    private void newGame() {
        emptyGraveyards();
        gameStarted=true;
        selectionBox.setVisible(false);
        game=new Game();
        setCheckerLocations();
        numWhiteDead = 0;
    	numBlackDead = 0;
    }
    
    private void addToGraveyard(Player p) {
    	if (p.equals(Player.BLACK)){
    		ImageView dead = new ImageView(new Image("/img/Wooden Board/Peasant_Black.png"));
    		dead.setLayoutX(numBlackDead*20);
    		blackGraveyard.getChildren().add(dead);
    		numBlackDead++;
    	}else {
    		ImageView dead = new ImageView(new Image("/img/Wooden Board/Peasant_White.png"));
    		dead.setLayoutX(numWhiteDead*20);
    		whiteGraveyard.getChildren().add(dead);
    		numWhiteDead++;
    	}
    }
    
    private void fillGraveyards() {
    	for (int i = 0; i<12; i++) {
    		addToGraveyard(Player.BLACK);
    		addToGraveyard(Player.WHITE);
    	}
    }
    
    private void emptyGraveyards() {
    	whiteGraveyard.getChildren().clear();
    	blackGraveyard.getChildren().clear();
    	numWhiteDead = 0;
    	numBlackDead = 0;
    }

    
    private void createSprites() {
    	blackSprite = new Image("/img/Wooden Board/black_occupied_tile.png");
    	whiteSprite = new Image("/img/Wooden Board/white_occupied_tile.png");
    	blackKingSprite = new Image("/img/Wooden Board/blackKing_tile.png");
    	whiteKingSprite = new Image("/img/Wooden Board/whiteKing_tile.png");
    	createSelectionBox();
    	createMoveBox();
    }

    private void commitTurn() {

        //game.commitMove(move);
    }

    private void onAction(Button b) {
        if (gameStarted) {
            int location = numSquares - locationMap.get(b);
            System.out.println(location);
            location = game.getBitRepresentation(location);
            System.out.println(location);
            int test=game.getNumRepresentation(location);
            System.out.println(test + "\n");
            if (game.spaceOccupied(location)) {
                PieceType type=game.getPieceType(location);
                if (spacePlayerOccupied(type)) {
                    setHighlight(b);
                    Piece piece = new Piece(type,location);
                    game.getValidMoves(piece);
                    showPossibleMoves(game.pieceMoves);
                    //use game.currentMoves to highlight moves of piece
                }
            } else {

            }
        }
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
        setLocations(game.board.whitePos, whiteSprite);
        setLocations(game.board.blackPos, blackSprite);
        setLocations(game.board.whiteKingPos, whiteKingSprite);
        setLocations(game.board.blackKingPos, blackKingSprite);
    }

    private void setLocations(int type, Image image) {
        int tempBoard=type;
        for (int counter=1; counter<33; counter++) {
            if ((tempBoard & 1)!=0) {
                addChecker(33-counter, image);
            }
            tempBoard=tempBoard>>1;
        }
    }

    private void addChecker(int location, Image image) {
        Button button = getButton(location);
        if (button!=null) {
            ImageView imgView=new ImageView(image);
            button.setGraphic(imgView);
        }
    }

	
	private void setHighlight(Button b) {
		selectedButton = (b.equals(selectedButton)) ? null : b;
		if (selectedButton != null) {
			selectionBox.setLayoutX(b.getLayoutX());
			selectionBox.setLayoutY(b.getLayoutY());
			selectionBox.setVisible(true);
            selectedChar=locationMap.get(b);
		}
		else {selectionBox.setVisible(false);}
	}

    private void showPossibleMoves(ArrayList<Move> currentMoves) {

    }

    private void createSelectionBox() {
        Image selectionImage = new Image("/img/Wooden Board/Selection_Highlight.png");
        selectionBox = new ImageView(selectionImage);
        selectionBox.setVisible(false);
        canvas.getChildren().add(selectionBox);
    }
    
    private void createMoveBox() {
    	Image moveImage = new Image("/img/Wooden Board/Move_Highlight.png");
    	moveBox = new ImageView(moveImage);
    	moveBox.setVisible(false);
    	canvas.getChildren().add(moveBox);
    }

    private void setUpBoxes() {
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium");
    }

    private boolean spacePlayerOccupied(PieceType type) {
        return type.equals(PieceType.BLACK) || type.equals(PieceType.BLACKKING);
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
            locationMap.put(button,idx);
        }
    }

    private Button getButton(int num) {
        return buttonMap.get(num);
    }
}