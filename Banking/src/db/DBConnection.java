package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	 public static Connection getConnection() {
	        try {
	            Class.forName("org.postgresql.Driver");

	            String url = "jdbc:postgresql://localhost:5432/BankingSystem";
	            String username = "postgres";
	            String password = "postgres";

	            return DriverManager.getConnection(url, username, password);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

}
