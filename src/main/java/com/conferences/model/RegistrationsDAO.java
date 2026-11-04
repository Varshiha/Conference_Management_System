package com.conferences.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;



public class RegistrationsDAO {
     
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");   
    private Logger logger = Logger.getLogger(RegistrationsDAO.class.getName());

    //Create operation
    public void addRegistration(Registration r) throws SQLException {
         String sql = "INSERT INTO Registrations (C_ID, U_ID, Registration_Date, Status, Waitinglist_Position) VALUES(?,?,?,?,?)";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setInt(1, r.getConferenceId());
                pstmt.setInt(2, r.getUserId());
                pstmt.setString(3, r.getRegistrationDate().format(formatter));
                pstmt.setString(4, r.getStatus());
                pstmt.setObject(5, r.getWaitinglistPosition());
                pstmt.executeUpdate();
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error adding registration: " + e.getMessage(), e);
                throw e;
            }
    }
    //Read operations
    public Registration getRegistrationById(int registrationId) throws SQLException {
        String sql = "SELECT * FROM Registrations WHERE R_ID = ?";
        Registration r = null;
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, registrationId);
                try(ResultSet rs = pstmt.executeQuery()){
                    if(rs.next()){
                        int R_ID = rs.getInt("R_ID");
                        int C_ID = rs.getInt("C_ID");
                        int U_ID = rs.getInt("U_ID");
                        LocalDateTime registrationDate = LocalDateTime.parse(rs.getString("Registration_Date"), formatter);
                        String status = rs.getString("Status");
                        Integer waitinglistPosition = (Integer) rs.getObject("Waitinglist_Position");
                        r = new Registration(R_ID, C_ID, U_ID, registrationDate, status, waitinglistPosition);
                    }
                }
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error retrieving registration by ID: " + e.getMessage(), e);
                throw e;
            }  
        return r;
    }

    public List<Registration> getRegistrationsByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM Registrations WHERE U_ID = ?";
        List<Registration> registrations = new ArrayList<>();
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, userId);
                try(ResultSet rs = pstmt.executeQuery()){
                    while(rs.next()){
                        int R_ID = rs.getInt("R_ID");
                        int C_ID = rs.getInt("C_ID");
                        int U_ID = rs.getInt("U_ID");
                        LocalDateTime registrationDate = LocalDateTime.parse(rs.getString("Registration_Date"), formatter);
                        String status = rs.getString("Status");
                        Integer waitinglistPosition = (Integer) rs.getObject("Waitinglist_Position");
                        registrations.add(new Registration(R_ID, C_ID, U_ID, registrationDate, status, waitinglistPosition));
                    }
                }
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error retrieving registration by user ID: " + e.getMessage(), e);
                throw e;
            }  
        return registrations;
    }

    public List<Registration> getRegistrationsByConference(int conferenceId) throws SQLException {
        String sql = "SELECT * FROM Registrations WHERE C_ID = ?";
        List<Registration> registrations = new ArrayList<>();
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, conferenceId);
                try(ResultSet rs = pstmt.executeQuery()){
                    while(rs.next()){
                        int R_ID = rs.getInt("R_ID");
                        int C_ID = rs.getInt("C_ID");
                        int U_ID = rs.getInt("U_ID");
                        LocalDateTime registrationDate = LocalDateTime.parse(rs.getString("Registration_Date"), formatter);
                        String status = rs.getString("Status");
                        Integer waitinglistPosition = (Integer) rs.getObject("Waitinglist_Position");
                        registrations.add(new Registration(R_ID, C_ID, U_ID, registrationDate, status, waitinglistPosition));
                    }
                }
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error retrieving registration by conference ID: " + e.getMessage(), e);
                throw e;
            }  
        return registrations;
    }

    //Update operation
    public void updateRegistration(Registration r) throws SQLException{
        String sql = "UPDATE Registrations SET Status = ?, Waitinglist_Position = ? WHERE R_ID = ?";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, r.getStatus());
                pstmt.setObject(2, r.getWaitinglistPosition());
                pstmt.setInt(3, r.getRegistrationId());
                pstmt.executeUpdate();
            }catch(SQLException e){
                logger.log(Level.SEVERE, "Error updating registration: " + e.getMessage(), e);
                throw e;
            }
    }

    //Delete operation
    public void deleteRegistration(int registrationID) throws SQLException{
        String sql = "DELETE FROM Registrations WHERE R_ID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setInt(1, registrationID);
                pstmt.executeUpdate();
            }catch(SQLException e){
                 logger.log(Level.SEVERE, "Error deleting registration: " + e.getMessage(), e);
            throw e;
            }
    }



}
