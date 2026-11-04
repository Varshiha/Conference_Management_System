package com.conferences;
import java.sql.SQLException;
import com.conferences.model.DatabaseConnection;

public class App {

	public static void main(String[] args) throws SQLException {
		DatabaseConnection.getConnection();
	}

}
