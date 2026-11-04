package com.conferences.model;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO 
{

    public UserDAO()
    {
        initializeAdmin();
    }

    public static String hashPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash)
            {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1)hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    //initializing the predefines admin account
    private void initializeAdmin()
    {
        String adminEmail = "admin@conference.com";
        String adminPassword = "AdminPassword123!";

        if(!emailExists(adminEmail))
        {
            registerUser("System Admin", adminEmail,adminPassword,19,"ADMIN");
        }
    }

    //checking the existence of the email in the DB
    public boolean emailExists(String email)
    {
        String sql = "SELECT U_ID FROM Credentials WHERE Email_Address = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1,email);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //registering a new user using the User and Credentials tables
    public boolean registerUser(String fullName,String email,String password, int age,String role)
    {
        //checking for unique email before registration
        if(emailExists(email))
        {
            return false;
        }

        String userSQL = "INSERT INTO Users(Full_Name,Age,Role) VALUES (?,?,?)";
        String credSQL = "INSERT INTO Credentials(U_ID, Email_Address,Password) Values (?,?,?)";


        Connection conn = null;
        try
        {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            //insertiing into user DB
            int userId = -1;
            try(PreparedStatement pstmtUser = conn.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS))
            {
                pstmtUser.setString(1,fullName);
                pstmtUser.setInt(2,age);
                pstmtUser.setString(3,role);
                pstmtUser.executeUpdate();

                try(ResultSet rs = pstmtUser.getGeneratedKeys()){

                    if(rs.next())
                    {
                        userId = rs.getInt(1);
                    }
                 }
            }

            if(userId ==-1)
            {
                conn.rollback();
                return false;
            }

            try(PreparedStatement pstmtCred = conn.prepareStatement(credSQL))
            {
                pstmtCred.setInt(1,userId);
                pstmtCred.setString(2,email);
                pstmtCred.setString(3,hashPassword(password));
                pstmtCred.executeUpdate();
            }
            conn.commit();
            return true;
        }

        catch(SQLException e)
        {
            if(conn != null)
            {
                try
                {
                    conn.rollback();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        }
        finally
        {
            if(conn!=null)
            {
                try
                {
                    conn.setAutoCommit(true);
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    //authenticate the user login
    public User authenticate(String email,String password)
    {
        String sql = "SELECT u.U_ID, u.Full_Name,c.Email_Address,c.Password,u.Age,u.Role "+
                     "FROM Users u JOIN Credentials c ON u.U_ID = c.U_ID "+
                     "WHERE c.Email_Address = ?";

        try(Connection conn = DatabaseConnection.getConnection();PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, email);
           try(ResultSet rs = pstmt.executeQuery()){

                if(rs.next())
                {
                    String storedHash = rs.getString("Password");

                    if(storedHash.equals(hashPassword(password)))
                    {
                         return new User(
                          rs.getInt("U_ID"),
                          rs.getString("Full_Name"),
                         rs.getString("Email_Address"),
                         storedHash,
                         rs.getInt("Age"),
                        rs.getString("Role")
                    );
                    }

                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserProfile(User user)
    {
        String sql = "UPDATE Users SET Full_Name = ?, Age=? where U_ID =?";
        try(Connection conn = DatabaseConnection.getConnection();PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, user.getFullName());
            pstmt.setInt(2, user.getAge());
            pstmt.setInt(3, user.getId());
            return pstmt.executeUpdate()>0;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}


