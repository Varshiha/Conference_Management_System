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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConferencesDAO {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");   
    private static final Logger logger = Logger.getLogger(ConferencesDAO.class.getName());

    public boolean addConference(Conference c) throws SQLException {
        String sql = "INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location) VALUES(?,?,?,?,?)";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstmt.setString(1, c.getTitle());
                pstmt.setInt(2, c.getCapacity());
                pstmt.setString(3, c.getStartDateTime().format(formatter));
                pstmt.setString(4, c.getEndDateTime().format(formatter));
                pstmt.setString(5, c.getLocation());
                pstmt.executeUpdate();
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if(rs.next()){
                        c.setConferenceID(rs.getInt(1));
                    }else{
                        throw new SQLException("Conference ID was not generated");
                    }
                }
                return true;
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error adding conference: ", e);
                throw e;
            }
    }

    public boolean deleteConference(int conferenceID) throws SQLException {
        String sql = "DELETE FROM Conferences WHERE C_ID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, conferenceID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }catch(SQLException e){
            logger.log(Level.SEVERE, "Error deleting conference: ", e);
            throw e;
        }
    }

    public boolean updateConference(Conference c) throws SQLException {
        String sql = "UPDATE Conferences SET Title = ?, Capacity = ?, Start_DateTime = ?, End_DateTime = ?, Location = ? WHERE C_ID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, c.getTitle());
                pstmt.setInt(2, c.getCapacity());           
                pstmt.setString(3, c.getStartDateTime().format(formatter));
                pstmt.setString(4, c.getEndDateTime().format(formatter));   
                pstmt.setString(5, c.getLocation());
                pstmt.setInt(6, c.getConferenceID());
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
        }catch(SQLException e){
            logger.log(Level.SEVERE, "Error updating conference: ", e);
            throw e;
        }
    }

    public Conference getConferenceByID(int conferenceID) throws SQLException {
        String sql = "SELECT * FROM Conferences WHERE C_ID = ?";
        Conference c = null;
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, conferenceID);
                try(ResultSet rs = pstmt.executeQuery()){
                    if(rs.next()){
                    int C_ID = rs.getInt("C_ID");
                    String title = rs.getString("Title");
                    int capacity = rs.getInt("Capacity");
                    LocalDateTime startDateTime = LocalDateTime.parse(rs.getString("Start_DateTime"), formatter);
                    LocalDateTime endDateTime = LocalDateTime.parse(rs.getString("End_DateTime"), formatter);
                    String location = rs.getString("Location");
                    Conference object = new Conference(C_ID, title, capacity, startDateTime, endDateTime, location);
                    c = object;
                    }
                }
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error retrieving conference by ID: ", e);
                throw e;
            }  
        return c;
    }

    public List<Conference> getAllConferences() throws SQLException {
        List<Conference> conferences = new ArrayList<>();
        String sql = "SELECT * FROM Conferences ORDER BY Start_DateTime";
        try(Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){
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
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error retrieving conferences: ", e);
                throw e;
            }

        return conferences;
    }
}
