package expertsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import game.*;

public class BasicAI extends InferenceEngine{
	
	public BasicAI() throws SQLException, ClassNotFoundException {
		super("basicAI");
	}
	
	public BasicAI(String tableName) throws SQLException, ClassNotFoundException {
		super(tableName);
	}
	
	public Move getMove(Board b, ArrayList<Move> legalMoves) throws SQLException{
		if(legalMoves.size() == 1) {
			return legalMoves.get(0);
		}

		int[] moveScores = new int[legalMoves.size()];
		ResultSet results = super.getMoveSet(b);
		
		results.last();
		int lastRow = results.getRow();
		results.first();
		
		String move;
		int score;
		while(results.getRow() <= lastRow) {
			move = results.getString("MoveDescription");
			score = results.getInt("Score");
			
			for(int i=0; i<legalMoves.size(); i++) {
				if(translateDirection(legalMoves.get(i)).equals(move.substring(2, 5))) {
					moveScores[i] += score;
				}
			}
			
			results.next();
		}
		
		results.close();
		
		Move suggestion = legalMoves.get(getIndexOfLargest(moveScores));
		return suggestion;
	}
	
	private String translateDirection(Move m) {
		int location = m.getLocation();
		int destination = m.getDestination();
		
		int closeMod = (int)Math.pow(2, 4);
		int farMod = (int)Math.pow(2, 5);
		
		if(m.isJump()) {
			closeMod = (int)Math.pow(2, 7);
			farMod = (int)Math.pow(2, 8);
		}
		
		if(location == Integer.MIN_VALUE) {
			if(destination == Math.pow(2, 27)) {
				return ">SW";
			} else {
				return ">SE";
			}
		} else if(location > destination && destination == location/closeMod) {
			return ">SW";
		} else if(location > destination && destination == location/farMod) {
			return ">SE";
		} else if(location < destination && location == destination/closeMod) {
			return ">NE";
		} else if(location < destination && location == destination/farMod) {
			return ">NW";
		}
		
		return "err";
	}
	
	private int getIndexOfLargest(int[] a) {
		int largest = 0;
		
		for(int i=1; i<a.length; i++) {
			if(a[i] >= a[largest])
				largest = i;
		}
		
		return largest;
	}
}
