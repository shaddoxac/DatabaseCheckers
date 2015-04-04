package expertsystem;

import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class KnowledgeDesigner {
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection db = DriverManager.getConnection("jdbc:sqlite:CheckersKnowledgeBase");
		Statement stmt = db.createStatement();
		
		Scanner jin = new Scanner(System.in);
		PrintStream jout = System.out;
		
		jout.print("TABLE?\t");
		String table = jin.next();
		
		jout.println("<====================>");
		jout.println("\tSYNTAX");
		jout.println("<====================>");
		jout.println("w - white pawn");
		jout.println("W - WHITE KING");
		jout.println("b - black pawn");
		jout.println("B - BLACK KING");
		jout.println("o - empty square");
		jout.println("Space - unused square");
		jout.println("<====================>\n");
		
		boolean done = false;
		while(!done) {
			String input = "";
			for(int a=0; a<4; a++) {
				jout.print(" ");
				for(int b=0; b<4; b++) {
					input +=jin.next();
				}
				jout.println();
				for(int b=0; b<4; b++) {
					input += jin.next();
				}
				jout.println();
			}
			
			String whitePawns = "",
					blackPawns = "",
					whiteKings = "",
					blackKings = "";
			
			for(int i=0; i<input.length(); i++) {
				switch(input.charAt(i)) {
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
			
			jout.print("\nMOVE?\tof form ##>[N/S][E/W]\t");
			String move = jin.next().toUpperCase();
			jout.print("SCORE?\t");
			int score = jin.nextInt();
			
			String insert = "INSERT INTO " + table + " VALUES ( \'" + whitePawns + "\', \'" + blackPawns + "\', \'" + whiteKings + "\', \'" + blackKings + "\', \'" + move + "\', " + score + " );";
			stmt.execute(insert);
			
			jout.print("\nCONTINUE?\t[Y/N]\t");
			if(jin.next().toUpperCase().charAt(0) == 'N') {
				done = false;
			}
		}
		
		jin.close();
	}
}
