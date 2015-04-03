package expertsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import game.*;

public class Intelligence {
	private final int PATTERN_MATCH_THRESHOLD = 3;
	Connection knowBase;
	String table;
	
	public Intelligence() throws SQLException, ClassNotFoundException {
		this("defaultTable");
	}
	
	public Intelligence(String tableName) throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		knowBase = DriverManager.getConnection("jdbc:sqlite:CheckersKnowledgeBase");
		table = tableName;
	}
	
	public Move[] getMove(Board b, ArrayList<Move> legalMoves) throws SQLException{
		if(legalMoves.size() == 1) {
			return legalMoves.toArray(new Move[1]);
		}
		
		Statement query = knowBase.createStatement();
		/*Scanner boardReader = new Scanner(b.toString());
		
		String whitePawns = boardReader.next(),
				blackPawns = boardReader.next(),
				whiteKings = boardReader.next(),
				blackKings = boardReader.next();
		boardReader.close();*/
		
		ResultSet results = query.executeQuery("SELECT moveCol,scoreCol FROM " + table + "WHERE ( \'" + b.toString() + "\' LIKE ( SELECT ifCol FROM ( SELECT ROWID FROM " + table + ") ) ) >= " + PATTERN_MATCH_THRESHOLD + " ORDER BY scoreCol DESC");
		
		
		return null;
	}
}
