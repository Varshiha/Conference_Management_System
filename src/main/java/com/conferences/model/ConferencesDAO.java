package com.conferences.model;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ConferencesDAO {

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS Conferences (C_ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT NOT NULL, Description TEXT NOT NULL, Capacity INTEGER NOT NULL, Start_DateTime TEXT NOT NULL, End_DateTime TEXT NOT NULL, Location TEXT NOT NULL)";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addConference(Conference c){
        String sql = "INSERT INTO Conferences (Title, Description, Capacity, Start_DateTime, End_DateTime, Location) VALUES(?,?,?,?,?,?)";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, c.getTitle());
                pstmt.setString(2, c.getDescription());
                pstmt.setInt(3, c.getCapacity());
                pstmt.setString(4, c.getStartDateTime().toString());
                pstmt.setString(5, c.getEndDateTime().toString());
                pstmt.setString(6, c.getLocation());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
}
