package com.conferences.tests;
import com.conferences.DAO.UserDAO;
import com.conferences.model.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UserDAOTest 
{
    @Test 
    public void testAdminInitialization()
    {
        UserDAO userDAO = new UserDAO();
        User admin = userDAO.authenticate("admin@conference.com", "AdminPassword123!");
        assertNotNull(admin,"Predefines admin should be successfully initialized and authenticated");
        assertEquals("ADMIN",admin.getRole());
    }

    @Test
    public void tesRegisterNewUser()
    {
        UserDAO userDAO = new UserDAO();
        String uniqueEmail = "user_"+System.currentTimeMillis()+"@gamil.com";

        boolean isRegistered = userDAO.registerUser("John doe", uniqueEmail, "Password123!", 23, "PARTICIPANT");
        assertTrue(isRegistered,"New user registration should work");
        assertTrue(userDAO.emailExists(uniqueEmail),"Email should be in DB after the registration");
    }

    @Test 
    public void testRegisterDuplicateEmail()
    {
        UserDAO userDAO = new UserDAO();
        String sharedEmail = "duplicate_"+System.currentTimeMillis()+"@gmail.com";

        boolean u1 = userDAO.registerUser("user1", sharedEmail, "pass123!", 28,"PARTICIPANT");
        assertTrue(u1);

        boolean u2 = userDAO.registerUser("user2", sharedEmail, "pass1234!", 24, "PARTICIPANT");
        assertFalse(u2,"Registartion should return false since we used a duplicate email");
    }

    @Test 
    public void testAuthenticattionSuccess()
    {
        UserDAO userDAO = new UserDAO();
        String email = "login_" + System.currentTimeMillis() + "@gmail.com";
        userDAO.registerUser("TestAcc", email, "PAssword1234!", 43, "PARTICIPANT");

        User loggedIn = userDAO.authenticate(email,"PAssword1234!");
        assertNotNull(loggedIn,"Authentication should work with the correct credentials");
        assertEquals("TestAcc", loggedIn.getFullName());
        
    }

    @Test 
    public void testAuthenticationFailWrongPassword()
    {
        UserDAO userDAO = new UserDAO();
        String email = "fail_" + System.currentTimeMillis() + "@gmail.com";
        userDAO.registerUser("Fail Guy", email, "CorrectPassword123",32,"PARTICIPANT");

        User loggedIn = userDAO.authenticate(email,"wrongpass1234");
        assertNull(loggedIn,"Authentication should fail because of the wrong password");
    }

   @Test
    public void testUpdateUserCredentials()
    {
        UserDAO userDAO = new UserDAO();
        String email = "profile_" + System.currentTimeMillis() + "@gmail.com";

        boolean created = userDAO.registerUser("mohammad guacamole", email, "OldPass123!", 21, "PARTICIPANT");
        assertTrue(created);

        User loggedIn = userDAO.authenticate(email, "OldPass123!");
        assertNotNull(loggedIn);

        loggedIn.setFullName("Mohammad Love Guacamole");
        loggedIn.setAge(19);
        boolean profileUpdated = userDAO.updateUserProfile(loggedIn);
        assertTrue(profileUpdated);

        String updatedEmail = "guac_" + System.currentTimeMillis() + "@gmail.com";
        loggedIn.setEmail(updatedEmail);
        loggedIn.setPassword("MoreGuac123!");
        boolean credsUpdated = userDAO.updateUserCredentials(loggedIn);
        assertTrue(credsUpdated);

        User reLogin = userDAO.authenticate(updatedEmail, "MoreGuac123!");
        assertNotNull(reLogin);
        assertEquals("Mohammad Love Guacamole", reLogin.getFullName());
        assertEquals(19, reLogin.getAge());
        assertNotEquals("MoreGuac123!", reLogin.getPassword());
    }
}
