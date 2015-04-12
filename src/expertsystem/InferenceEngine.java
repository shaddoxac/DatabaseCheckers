package expertsystem;

import game.Board;
import game.Move;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class InferenceEngine {
	private final int GENERIC_PATTERN_MATCH_THRESHOLD = 3;
	private final int WHT_PAWN_THRESHOLD = GENERIC_PATTERN_MATCH_THRESHOLD;
	private final int BLK_PAWN_THRESHOLD = GENERIC_PATTERN_MATCH_THRESHOLD;
	private final int WHT_KING_THRESHOLD = GENERIC_PATTERN_MATCH_THRESHOLD;
	private final int BLK_KING_THRESHOLD = GENERIC_PATTERN_MATCH_THRESHOLD;
	
	protected Connection knowBase;
	protected String table;
	
	public InferenceEngine(String tableName) throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		knowBase = DriverManager.getConnection("jdbc:sqlite:CheckersKnowledgeBase");
		table = tableName;
	}
	
	protected ResultSet getMoveSet(Board b) throws SQLException {
		Statement query = knowBase.createStatement();
		Scanner reader = new Scanner(b.toString());
		
		String whitePawns = b.toWhiteString(),
				blackPawns = b.toBlackString(),
				whiteKings = b.toWhiteKingString(),
				blackKings = b.toBlackKingString();
		
		String queryString = "";
		try {
			reader = new Scanner(new FileReader(new File("SQL_QUERY")));
			reader.useDelimiter("#");
			
			String part;
			while(reader.hasNext()) {
				part = reader.next();
				
				if(part.equals("tableName"))
					queryString += table;
				else if(part.equals("whitePawns"))
					queryString += whitePawns;
				else if(part.equals("blackPawns"))
					queryString += blackPawns;
				else if(part.equals("whiteKings"))
					queryString += whiteKings;
				else if(part.equals("blackPawns"))
					queryString += blackKings;
				else if(part.equals("whitePawnThreshold"))
					queryString += WHT_PAWN_THRESHOLD;
				else if(part.equals("blackPawnThreshold"))
					queryString += BLK_PAWN_THRESHOLD;
				else if(part.equals("whiteKingThreshold"))
					queryString += WHT_KING_THRESHOLD;
				else if(part.equals("blackKingThreshold"))
					queryString += BLK_KING_THRESHOLD;
				else
					queryString += part;
			}
		} catch(IOException x) {
			System.err.println("ERROR: Failed to access query file; unable to query database.");
			System.exit(1);
		}
		
		reader.close();
		return query.executeQuery(queryString);
	}
	
	public abstract Move getMove(Board b, ArrayList<Move> legalMoves) throws SQLException;
}
