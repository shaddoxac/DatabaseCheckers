package GUI;

import expertsystem.*;
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

import java.sql.SQLException;
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

	private Image emptyTile, whiteSprite, blackSprite, whiteKingSprite, blackKingSprite, moveBox;
    private ImageView selectionBox, cpuBox1, cpuBox2;
	private Game game;
    private int turnCount=0;
    private Button selectedButton;
    private HashMap<Integer,Button> buttonMap =new HashMap<>();
    private HashMap<Button,Integer> locationMap=new HashMap<>();
    private boolean gameStarted=false;
    private final int numSquares = 32;
    private int numWhiteDead = 0;
    private int numBlackDead = 0;
    private ArrayList<Button> legalMoves = new ArrayList<>();
    private InferenceEngine ai;
    private int outOfBounds = 5000;

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
    	turnCount = 0;
    	numMoves.setText(Integer.toString(turnCount));
        emptyGraveyards();
        gameStarted=true;
        selectionBox.setVisible(false);
        setUpAI();
        game=new Game(ai);
        setCheckerLocations();
        setPlayerTurn();
        emptyGraveyards();
    	deselect();
    	hideComputerMove();
    }
    
    private void gameOver() {
    	gameStarted = false;
    	turnIndicator.setText(game.winner.toString() + " wins!");
    	String color = game.winner.equals(Player.BLACK) ? "black" : "white";
        turnIndicator.setStyle("-fx-text-inner-color: " + color + ";");
    }
    
    private void checkGameOver() {
    	if (game.gameOver) {gameOver();}
    }
    
    private void setUpAI()  {
    	String difficulty = difficultyBox.getValue();
        try {
            if (difficulty.equals("Easy")) {
                ai = new RandomAI();
            }
            else {
                ai = new BasicAI();
            }
        }
        catch (SQLException ex) {ex.printStackTrace();}
        catch (ClassNotFoundException ex2) {ex2.printStackTrace();}
    }
    
    private void updateGraveyards() {
    	int newWhiteDead = game.getNumDead(Player.WHITE) - numWhiteDead;
		int newBlackDead = game.getNumDead(Player.BLACK) - numBlackDead;
		if (newWhiteDead != 0){addToGraveyard(Player.WHITE);}
		if (newBlackDead != 0){addToGraveyard(Player.BLACK);}
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

    private void setUpBoxes() {
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium");
    }
    
    private void createSprites() {
    	emptyTile = new Image("/img/Wooden Board/black_tile.png");
    	blackSprite = new Image("/img/Wooden Board/black_occupied_tile.png");
    	whiteSprite = new Image("/img/Wooden Board/white_occupied_tile.png");
    	blackKingSprite = new Image("/img/Wooden Board/blackKing_tile.png");
    	whiteKingSprite = new Image("/img/Wooden Board/whiteKing_tile.png");
    	moveBox = new Image("/img/Wooden Board/move_tile.png");
    	createCPUBoxes();
    	createSelectionBox();
    }
    
    private void createSelectionBox() {
        Image selectionImage = new Image("/img/Wooden Board/Selection_Highlight.png");
        selectionBox = new ImageView(selectionImage);
        selectionBox.setVisible(false);
        canvas.getChildren().add(selectionBox);
    }
    
    private void createCPUBoxes() {
    	Image img1 = new Image("/img/Wooden Board/CPU_Highlight.png");
    	Image img2 = new Image("/img/Wooden Board/CPU_Highlight.png");
    	cpuBox1 = new ImageView(img1);
    	cpuBox2 = new ImageView(img2);
    	cpuBox1.setLayoutX(outOfBounds);
    	cpuBox1.setLayoutY(outOfBounds);
    	cpuBox2.setLayoutX(outOfBounds);
    	cpuBox2.setLayoutY(outOfBounds);
    	cpuBox1.setVisible(false);
    	cpuBox2.setVisible(false);
    	canvas.getChildren().add(cpuBox1);
    	canvas.getChildren().add(cpuBox2);
    }

    private void clearLegalMoves() {
    	if (!legalMoves.isEmpty()){
    		for (Button move : legalMoves) {
        		move.setGraphic(new ImageView(emptyTile));
        	}
    	}
        legalMoves.clear();
    }
    
    private void updateTurnCount() {
        turnCount++;
        numMoves.setText(Integer.toString(turnCount));
    }
    
    private void setSelected(Button b) {
		selectedButton = b;
		if (selectedButton != null) {
			selectionBox.setLayoutX(b.getLayoutX());
			selectionBox.setLayoutY(b.getLayoutY());
			selectionBox.setVisible(true);
		}
		else {selectionBox.setVisible(false);}
	}
	
    @FXML
	private void deselect() {
    	if (gameStarted){
			selectionBox.setVisible(false);
			selectedButton = null;
			clearLegalMoves();
			showComputerMove();
		}
    }
	
	private void switchTurns() {
		updateGraveyards();
        if (!game.gameOver) {
            String currentTurn = turnIndicator.getText();
            boolean isPlayerTurn = currentTurn.equals("Your turn");
            String newTurn = isPlayerTurn ? "CPU turn" : "Your turn";
            String newColor = isPlayerTurn ? "white" : "black";
            turnIndicator.setText(newTurn);
            turnIndicator.setStyle("-fx-text-inner-color: " + newColor + ";");
            game.changeTurn();
            checkGameOver();
            if (!game.isPlayerTurn()) {
                hideComputerMove();
                requestAIMove();
            }
        }
	}
	
	private void setPlayerTurn() {
		turnIndicator.setText("Your turn");
		turnIndicator.setStyle("-fx-text-inner-color: black;");
	}
	
	private void requestAIMove() {
		if (gameStarted) {
        	Move move=game.commitAIMove();
	        highlightComputerMove(move);
			updateTurnCount();
	        setCheckerLocations();
	        deselect();
			switchTurns();
		}
	}

    private void highlightComputerMove(Move move) {
    	int startLocation = numSquares+1 - game.getNumRepresentation(move.getLocation());
    	Button startTile = getButton(startLocation);
    	int endLocation = numSquares+1 - game.getNumRepresentation(move.getDestination());
    	Button endTile = getButton(endLocation);
    	cpuBox1.setLayoutX(startTile.getLayoutX());
    	cpuBox1.setLayoutY(startTile.getLayoutY());
    	cpuBox1.setVisible(true);
    	cpuBox2.setLayoutX(endTile.getLayoutX());
    	cpuBox2.setLayoutY(endTile.getLayoutY());
    	cpuBox2.setVisible(true);
    }
    
    private void showComputerMove() {
    	cpuBox1.setVisible(true);
    	cpuBox2.setVisible(true);
    }
    
    private void hideComputerMove() {
    	cpuBox1.setVisible(false);
    	cpuBox2.setVisible(false);
    }
    
    private void onAction(Button b) {
        if (gameStarted) {
            clearLegalMoves();
            if (!game.gameOver && game.isPlayerTurn()) {
                int location = numSquares + 1 - locationMap.get(b);
                location = game.getBitRepresentation(location);
                if (game.spaceOccupied(location)) {
                    PieceType type = game.getPieceType(location);
                    if (spacePlayerOccupied(type)) {
                        setSelected(b);
                        Piece piece = new Piece(type, location);
                        game.getMovesForPiece(piece);
                        showPossibleMoves(game.pieceMoves);
                    }
                } else {
                    selectMove(b);
                }
            }
        }
    }
    
    private boolean spacePlayerOccupied(PieceType type) {
        return type.equals(PieceType.BLACK) || type.equals(PieceType.BLACKKING);
    }
    
    private void showPossibleMoves(ArrayList<Move> currentMoves) {
    	for (Move move : currentMoves) {
    		int tileNum = (numSquares+1) - game.getNumRepresentation(move.getDestination());
    		Button tileButton = getButton(tileNum);
    		legalMoves.add(tileButton);
    		ImageView moveTile = new ImageView(moveBox);
    		tileButton.setGraphic(moveTile);
    		hideComputerMove();
    	}
    }
    
    private void selectMove(Button b) {
    	if (selectedButton != null){
        	int moveLocation = numSquares+1 - locationMap.get(b);
        	int moveBitLocation = game.getBitRepresentation(moveLocation);
            for (int idx=0; idx<game.pieceMoves.size(); idx++) {
                if (game.pieceMoves.get(idx).getDestination()==moveBitLocation) {
                    game.commitMove(game.pieceMoves.get(idx));
                    updateTurnCount();
                    setCheckerLocations();
                    deselect();
					switchTurns();
                }
            }deselect();
    	}
    }
    
    private void setCheckerLocations() {
        clearAllTiles();
        setLocations(game.board.whitePos, whiteSprite);
        setLocations(game.board.blackPos, blackSprite);
        setLocations(game.board.whiteKingPos, whiteKingSprite);
        setLocations(game.board.blackKingPos, blackKingSprite);
    }

    private void clearAllTiles() {
        ImageView imgView;
        for (int i=1; i<=numSquares; i++) {
            imgView=new ImageView(emptyTile);
            getButton(i).setGraphic(imgView);
        }
    }

    private void setLocations(int type, Image checker) {
        int tempBoard=type;
        for (int counter=1; counter<numSquares+1; counter++) {
            if ((tempBoard & 1)!=0) {
                addChecker(numSquares+1-counter, checker);
            }
            tempBoard=tempBoard>>1;
        }
    }
    
    private void addChecker(int location, Image checker) {
        Button button = getButton(location);
        if (button!=null) {
            ImageView imgView=new ImageView(checker);
            button.setGraphic(imgView);
        }
    }
    
    private Button getButton(int num) {
        return buttonMap.get(num);
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
        setButtonActions();
    }

    private void setButtonActions() {
        for (int idx=1; idx<= numSquares; idx++) {
            Button button= getButton(idx);
            button.setOnAction((event) -> {
                onAction(button);
            });
            locationMap.put(button,idx);
        }
    }
}