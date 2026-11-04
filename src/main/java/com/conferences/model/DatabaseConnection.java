package com.conferences.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:Conferences.db";
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection getConnection()throws SQLException {
        Connection con = DriverManager.getConnection(URL);
        try(Statement stmt = con.createStatement()){
            stmt.execute("PRAGMA foreign_keys = ON");
            return con;
        }catch(SQLException e){
            con.close();
            logger.log(Level.SEVERE, "Error enabling foreign keys: ", e);
            throw e;
        }
    }
}
