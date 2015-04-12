package easysql;
import java.sql.*;
import java.util.Scanner;

// Most useful for reading material from a database
// Make sure that sqlite-jdbc.3.8.7.jar is in the proper directory
public class Reader {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:CheckersKnowledgeBase");
        Statement stat = con.createStatement();

        boolean done = false;
        Scanner input = new Scanner(System.in);
        while (!done) {
            System.out.print("Enter command: ");
            String cmd = input.nextLine();
            if (cmd.equals("quit") || cmd.equals("exit")) {
                done = true;
            } else {
                System.out.print("Enter # of columns expected: ");
                int columns = input.nextInt();
                input.nextLine();
                if (stat.execute(cmd)) {
                    ResultSet results = stat.getResultSet();
                    while (results.next()) {
                        for (int c = 1; c <= columns; ++c) {
                            System.out.print(results.getString(c) + "\t");
                        }
                        System.out.println();
                    } 
                }
            }
        }
	input.close();
    }
}