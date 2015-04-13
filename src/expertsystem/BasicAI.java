package expertsystem;

import game.Board;
import game.Move;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasicAI extends InferenceEngine{
	
	public BasicAI() throws SQLException, ClassNotFoundException {
		super("basicAI");
	}
	
	public BasicAI(String tableName) throws SQLException, ClassNotFoundException {
		super(tableName);
	}
	
	@Override
	public Move getMove(Board b, ArrayList<Move> legalMoves) throws SQLException{
		if(legalMoves.size() == 1) {
			return legalMoves.get(0);
		}

		int[] moveScores = new int[legalMoves.size()];
		ResultSet results = super.getMoveSet(b);
		String move;
		int score;

		while(results.next()) {
			move = results.getString("MoveDescription");
			score = results.getInt("Score");
            System.out.println("\nmove = "+move);
			for(int i=0; i<legalMoves.size(); i++) {
                System.out.println("score ="+score);
                System.out.println("translate: "+translateDirection(legalMoves.get(i)).equals(move.substring(move.length()-2, move.length())));
                System.out.println("similar loc: "+isSimilarLocation(legalMoves.get(i), move));
				if(translateDirection(legalMoves.get(i)).equals(move.substring(move.length()-2, move.length())) && isSimilarLocation(legalMoves.get(i), move)) {
					moveScores[i] += score;
					if(isSameLocation(legalMoves.get(i), move)) {
                        System.out.println("SameLocation is true");
                        moveScores[i]++;
                    }
				}
			}
		}
		
		results.close();

        int idx=0;//TODO erase
        for (Move moves : legalMoves) {
            System.out.println(getNumRepresentation(moves.getLocation())-1 + " "+(getNumRepresentation(moves.getDestination())-1)+" "+moveScores[idx]);
            idx++;
        }
		Move suggestion = legalMoves.get(getIndexOfLargest(moveScores));
		return suggestion;
	}
	
	private boolean isSimilarLocation(Move m, String suggestion) {
		int suggestedLoc = Integer.parseInt(suggestion.substring(0, suggestion.length()-3));
        suggestedLoc = (int) Math.pow(2,suggestedLoc);
		int moveLocation = m.getLocation();
		
		return getRow(moveLocation) == getRow(suggestedLoc) || getCol(moveLocation) == getCol(suggestedLoc);
	}
	
	private boolean isSameLocation(Move m, String suggestion) {
		int suggestedLoc = Integer.parseInt(suggestion.substring(0, suggestion.length()-3));
        suggestedLoc = (int) Math.pow(2,suggestedLoc);
		int moveLocation = m.getLocation();


		
		return getRow(moveLocation) == getRow(suggestedLoc) && getCol(moveLocation) == getCol(suggestedLoc);
	}

    public int getNumRepresentation(int bits) {//TODO erase
        if (bits==0x80000000) {return 32;}
        int counter=1;
        while (bits>1) {
            bits=bits >> 1;
            counter++;
        }
        return counter;
    }
	
	private int getCol(int location) {
		switch(location) {
		case Integer.MIN_VALUE:
		case Integer.MIN_VALUE >>> 8:
		case Integer.MIN_VALUE >>> 16:
		case Integer.MIN_VALUE >>> 24:
			return 1;
		case Integer.MIN_VALUE >>> 1:
		case Integer.MIN_VALUE >>> 9:
		case Integer.MIN_VALUE >>> 17:
		case Integer.MIN_VALUE >>> 25:
			return 3;
		case Integer.MIN_VALUE >>> 2:
		case Integer.MIN_VALUE >>> 10:
		case Integer.MIN_VALUE >>> 18:
		case Integer.MIN_VALUE >>> 26:
			return 5;
		case Integer.MIN_VALUE >>> 3:
		case Integer.MIN_VALUE >>> 11:
		case Integer.MIN_VALUE >>> 19:
		case Integer.MIN_VALUE >>> 27:
			return 7;
		case Integer.MIN_VALUE >>> 4:
		case Integer.MIN_VALUE >>> 12:
		case Integer.MIN_VALUE >>> 20:
		case Integer.MIN_VALUE >>> 28:
			return 0;
		case Integer.MIN_VALUE >>> 5:
		case Integer.MIN_VALUE >>> 13:
		case Integer.MIN_VALUE >>> 21:
		case Integer.MIN_VALUE >>> 29:
			return 2;
		case Integer.MIN_VALUE >>> 6:
		case Integer.MIN_VALUE >>> 14:
		case Integer.MIN_VALUE >>> 22:
		case Integer.MIN_VALUE >>> 30:
			return 4;
		default:
			return 6;
		}
	}
	
	private int getRow(int location) {
		switch(location) {
		case Integer.MIN_VALUE:
		case Integer.MIN_VALUE >>> 1:
		case Integer.MIN_VALUE >>> 2:
		case Integer.MIN_VALUE >>> 3:
			return 0;
		case Integer.MIN_VALUE >>> 4:
		case Integer.MIN_VALUE >>> 5:
		case Integer.MIN_VALUE >>> 6:
		case Integer.MIN_VALUE >>> 7:
			return 1;
		case Integer.MIN_VALUE >>> 8:
		case Integer.MIN_VALUE >>> 9:
		case Integer.MIN_VALUE >>> 10:
		case Integer.MIN_VALUE >>> 11:
			return 2;
		case Integer.MIN_VALUE >>> 12:
		case Integer.MIN_VALUE >>> 13:
		case Integer.MIN_VALUE >>> 14:
		case Integer.MIN_VALUE >>> 15:
			return 3;
		case Integer.MIN_VALUE >>> 16:
		case Integer.MIN_VALUE >>> 17:
		case Integer.MIN_VALUE >>> 18:
		case Integer.MIN_VALUE >>> 19:
			return 4;
		case Integer.MIN_VALUE >>> 20:
		case Integer.MIN_VALUE >>> 21:
		case Integer.MIN_VALUE >>> 22:
		case Integer.MIN_VALUE >>> 23:
			return 5;
		case Integer.MIN_VALUE >>> 24:
		case Integer.MIN_VALUE >>> 25:
		case Integer.MIN_VALUE >>> 26:
		case Integer.MIN_VALUE >>> 27:
			return 6;
		default:
			return 7;
		}
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
				return "SW";
			} else {
				return "SE";
			}
		} else if((location > destination) && (destination == location/closeMod)) {
			return "SW";
		} else if((location > destination) && (destination == location/farMod)) {
			return "SE";
		} else if((location < destination) && (location == destination/closeMod)) {
			return "NE";
		} else if((location < destination && location == destination/farMod)) {
			return "NW";
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
