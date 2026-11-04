package com.conferences.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:Conferences.db";

    public static Connection getConnection()throws SQLException {
        Connection con = DriverManager.getConnection(URL);
        try(Statement stmt = con.createStatement()){
            stmt.execute("PRAGMA foreign_keys = ON");
            return con;
        }catch(SQLException e){
            con.close();
            System.err.println("Error enabling foreign keys: " + e.getMessage());
           throw e;
        }
    }
}
