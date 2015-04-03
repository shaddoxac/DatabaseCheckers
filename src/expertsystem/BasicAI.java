package expertsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import game.*;

public class BasicAI extends InferenceEngine{
	
	public BasicAI() throws SQLException, ClassNotFoundException {
		super("defaultTable");
	}
	
	public BasicAI(String tableName) throws SQLException, ClassNotFoundException {
		super(tableName);
	}
	
	public ArrayList<Move> getMove(Board b, ArrayList<Move> legalMoves) throws SQLException{
		if(legalMoves.size() == 1) {
			return legalMoves;
		}
		
		ResultSet results = super.getMoveSet(b);
		ArrayList<Move> suggestedMoves = new ArrayList<Move>();
		results.last();
		int lastRow = results.getRow();
		results.first();
		
		String move, score;
		while(results.getRow() <= lastRow) {
			move = results.getString("moveCol");
			score = results.getString("scoreCol");
			
			
			
			results.next();
		}
		
		results.close();
		return suggestedMoves;
	}
}
