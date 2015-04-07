package expertsystem;

import game.Board;
import game.Move;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class RandomAI extends InferenceEngine {
    Random rand=new Random();
	
	public RandomAI() throws ClassNotFoundException, SQLException {
		super("randomTable");
	}
	
	public RandomAI(String tableName) throws ClassNotFoundException, SQLException {
		super(tableName);
	}
	
	public Move getMove(Board b, ArrayList<Move> legalMoves) {
		return legalMoves.get(rand.nextInt(legalMoves.size()));
	}
}