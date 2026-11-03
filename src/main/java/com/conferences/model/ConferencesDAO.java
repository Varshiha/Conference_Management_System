package com.conferences.model;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConferencesDAO {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");   
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Conferences (C_ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT NOT NULL, Capacity INTEGER NOT NULL CHECK(Capacity > 0), Start_DateTime TEXT NOT NULL, End_DateTime TEXT NOT NULL CHECK (End_DateTime > Start_DateTime), Location TEXT)";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addConference(Conference c) throws SQLException {
        String sql = "INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location) VALUES(?,?,?,?,?)";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, c.getTitle());
                pstmt.setInt(2, c.getCapacity());
                pstmt.setString(3, c.getStartDateTime().format(formatter));
                pstmt.setString(4, c.getEndDateTime().format(formatter));
                pstmt.setString(5, c.getLocation());
                pstmt.executeUpdate();
            }
    }

    public void deleteConference(int conferenceID) throws SQLException {
        String sql = "DELETE FROM Conferences WHERE C_ID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, conferenceID);
            pstmt.executeUpdate();
        }
    }

    public void updateConference(Conference c) throws SQLException {
        String sql = "UPDATE Conferences SET Title = ?, Capacity = ?, Start_DateTime = ?, End_DateTime = ?, Location = ? WHERE C_ID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, c.getTitle());
                pstmt.setInt(2, c.getCapacity());           
                pstmt.setString(3, c.getStartDateTime().format(formatter));
                pstmt.setString(4, c.getEndDateTime().format(formatter));   
                pstmt.setString(5, c.getLocation());
                pstmt.setInt(6, c.getConferenceID());
                pstmt.executeUpdate();
        }
    }

    public Conference getConferenceByID(int conferenceID) throws SQLException {
        String sql = "SELECT * FROM Conferences WHERE C_ID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, conferenceID);
                try(ResultSet rs = pstmt.executeQuery()){
                    if(rs.next()){
                    String title = rs.getString("Title");
                    int capacity = rs.getInt("Capacity");
                    LocalDateTime startDateTime = LocalDateTime.parse(rs.getString("Start_DateTime"), formatter);
                    LocalDateTime endDateTime = LocalDateTime.parse(rs.getString("End_DateTime"), formatter);
                    String location = rs.getString("Location");
                    Conference c = new Conference(conferenceID, title, capacity, startDateTime, endDateTime, location);
                    return c;
                    }
                }
            }
        return null;
    }

    public List<Conference> getAllConferences() throws SQLException {
        List<Conference> conferences = new ArrayList<>();
        String sql = "SELECT * FROM Conferences ORDER BY Start_DateTime";
        try(Connection con = DatabaseConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
                while(rs.next()){
                    int conferenceID = rs.getInt("C_ID");
                    String title = rs.getString("Title");
                    int capacity = rs.getInt("Capacity");
                    LocalDateTime startDateTime = LocalDateTime.parse(rs.getString("Start_DateTime"), formatter);
                    LocalDateTime endDateTime = LocalDateTime.parse(rs.getString("End_DateTime"), formatter);
                    String location = rs.getString("Location");
                    Conference c = new Conference(conferenceID, title, capacity, startDateTime, endDateTime, location);
                    conferences.add(c);
                }
            }
        return conferences;
    }
}
