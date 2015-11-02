package szeljic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static Connection connection = null;
	
	public static Connection openConnection() {
//		return DriverManager.getConnection("jdbc:sqlite:gui_terminal_commands.db");
		return null;
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
