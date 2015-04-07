package knowledgedesigner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class KnowledgeDesignerController {
	
	private Connection knowBase;
	private Statement stmt;
	
	@FXML
	GridPane pieceGrid;
	@FXML
	ToggleGroup pieceType;
	@FXML
	RadioButton whitePawnButton;
	@FXML
	RadioButton blackPawnButton;
	@FXML
	RadioButton whiteKingButton;
	@FXML
	RadioButton blackKingButton;
	@FXML
	TextField movePiece;
	@FXML
	TextField moveDir;
	@FXML
	TextField score;
	@FXML
	TextField table;
	@FXML
	Button create;
	
	Image whitePawnImg = new Image("/img/Wooden Board/Peasant_White.png"),
			blackPawnImg = new Image("/img/Wooden Board/Peasant_Black.png"),
			whiteKingImg = new Image("/img/Wooden Board/King_White.png"),
			blackKingImg = new Image("/img/Wooden Board/King_Black.png"),
			blankTileImg = new Image("/img/Wooden Board/black_tile.png");
	
	private char[] pieces;
	
	@FXML
	private void initialize() {
		try {
			Class.forName("org.sqlite.JDBC");
			knowBase = DriverManager.getConnection("jdbc:sqlite:CheckersKnowledgeBase");
			stmt = knowBase.createStatement();
			setup();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setup() {
		pieces = new char[32];
		for(int i=0; i<32; i++) {
			pieces[i] = 'e';
		}
		
		int idx = 31;
		for(int r=0; r<8; r += 2) {
			for(int c=0; c<8; c += 2) {
				pieceGrid.add(new PieceView(idx), c+1, r);
				idx--;
				pieceGrid.add(new PieceView(idx), c, r+1);
				idx--;
			}
		}
	}
	
	@FXML
	public void createClick() {
		String board = "";
		for(int i=31; i>=0; i--) {
			board += pieces[i];
		}
		
		int firstSignificantVal = board.length(),
				lastSignificantVal = -1;
		
		if(board.indexOf('w') != -1 && board.indexOf('w') < firstSignificantVal)
			firstSignificantVal = board.indexOf('w');
		if(board.indexOf('W') != -1 && board.indexOf('W') < firstSignificantVal)
			firstSignificantVal = board.indexOf('W');
		if(board.indexOf('b') != -1 && board.indexOf('b') < firstSignificantVal)
			firstSignificantVal = board.indexOf('b');
		if(board.indexOf('B') != -1 && board.indexOf('B') < firstSignificantVal)
			firstSignificantVal = board.indexOf('B');
		if(firstSignificantVal == board.length())
			firstSignificantVal = 0;
		
		if(board.lastIndexOf('w') > lastSignificantVal)
			lastSignificantVal = board.lastIndexOf('w');
		if(board.lastIndexOf('W') > lastSignificantVal)
			lastSignificantVal = board.lastIndexOf('W');
		if(board.lastIndexOf('b') > lastSignificantVal)
			lastSignificantVal = board.lastIndexOf('b');
		if(board.lastIndexOf('B') < firstSignificantVal)
			lastSignificantVal = board.lastIndexOf('B');
		if(lastSignificantVal == -1)
			lastSignificantVal = board.length()-1;
		
		board = board.substring(firstSignificantVal, lastSignificantVal+1);
		
		String whitePawns = "%",
				blackPawns = "%",
				whiteKings = "%",
				blackKings = "%";
		
		for(int i=0; i<board.length(); i++) {
			switch(board.charAt(i)) {
			case 'w':
				whitePawns += "1";
				blackPawns += "_";
				whiteKings += "_";
				blackKings += "_";
				break;
			case 'W':
				whitePawns += "_";
				blackPawns += "_";
				whiteKings += "1";
				blackKings += "_";
				break;
			case 'b':
				whitePawns += "_";
				blackPawns += "1";
				whiteKings += "_";
				blackKings += "_";
				break;
			case 'B':
				whitePawns += "_";
				blackPawns += "_";
				whiteKings += "_";
				blackKings += "1";
				break;
			default:
				whitePawns += "_";
				blackPawns += "_";
				whiteKings += "_";
				blackKings += "_";
				break;
			}
		}
		
		whitePawns += "%";
		blackPawns += "%";
		whiteKings += "%";
		blackKings += "%";
		String move = movePiece.getText() + ">" + moveDir.getText().toUpperCase();
		
		String insert = "INSERT INTO " + table.getText() + " VALUES ( \'" + whitePawns + "\', \'" + blackPawns + "\', \'" + whiteKings + "\', \'" + blackKings + "\', \'" + move + "\', " + score.getText() + " );";
		
		try {
			stmt.execute(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ObservableList<Node> grid = pieceGrid.getChildren();
		int idx = 31;
		for(Node n: grid) {
			n = new PieceView(idx);
			idx--;
		}
		setup();
	}
	
	private char getPieceType() {
		if(whitePawnButton.isSelected())
			return 'w';
		else if(blackPawnButton.isSelected())
			return 'b';
		else if(whiteKingButton.isSelected())
			return 'W';
		else if(blackKingButton.isSelected())
			return 'B';
		
		return 'x';
	}
	
	private class PieceView extends ImageView {
		private int idx;
		
		public PieceView(int idx) {
			super();
			this.idx = idx;
			this.setImage(blankTileImg);
			
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.pieceClick());
			this.setCursor(Cursor.HAND);
		}
		
		private void pieceClick() {
			char pieceType = getPieceType();
			
			if(pieceType == pieces[idx]) {
				this.setImage(blankTileImg);
				pieces[idx] = 'e';
			} else {
				switch(pieceType) {
				case 'w':
					this.setImage(whitePawnImg);
					break;
				case 'b':
					this.setImage(blackPawnImg);
					break;
				case 'W':
					this.setImage(whiteKingImg);
					break;
				case 'B':
					this.setImage(blackKingImg);
					break;
				}
				
				pieces[idx] = pieceType;
			}
		}
	}
}
