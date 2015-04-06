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
		jout.println("    Optional:");
		jout.println("x - unused square");
		jout.println("<====================>\n");
		
		boolean done = false;
		while(!done) {
			String input = "";
			for(int a=1; a<=8; a++) {
				jout.print("ROW " + a + ":\t");
				input += jin.nextLine();
			}
			input = input.replaceAll("x", "");
			
			int firstSignificantVal = input.length(),
					lastSignificantVal = input.lastIndexOf('w');
			
			if(input.indexOf('w') != -1 && input.indexOf('w') < firstSignificantVal)
				firstSignificantVal = input.indexOf('W');
			if(input.indexOf('W') != -1 && input.indexOf('W') < firstSignificantVal)
				firstSignificantVal = input.indexOf('W');
			if(input.indexOf('b') != -1 && input.indexOf('b') < firstSignificantVal)
				firstSignificantVal = input.indexOf('b');
			if(input.indexOf('B') != -1 && input.indexOf('B') < firstSignificantVal)
				firstSignificantVal = input.indexOf('B');
			
			if(input.lastIndexOf('W') > lastSignificantVal)
				lastSignificantVal = input.lastIndexOf('W');
			if(input.lastIndexOf('b') > lastSignificantVal)
				lastSignificantVal = input.lastIndexOf('b');
			if(input.lastIndexOf('B') < firstSignificantVal)
				lastSignificantVal = input.lastIndexOf('B');
			
			jout.println(firstSignificantVal + ", " + lastSignificantVal);
			jout.println(input);
			input = input.substring(firstSignificantVal, lastSignificantVal+1);
			jout.println(input);
			
			String whitePawns = "%",
					blackPawns = "%",
					whiteKings = "%",
					blackKings = "%";
			
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
			
			whitePawns += "%";
			blackPawns += "%";
			whiteKings += "%";
			blackKings += "%";
			
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
