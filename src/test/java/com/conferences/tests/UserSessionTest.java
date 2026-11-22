package com.conferences.tests;
import com.conferences.model.User;
import com.conferences.model.UserSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserSessionTest 
{
    @AfterEach 
    public void tearDown()
    {
        UserSession.clearSession();
    }

    @Test
    public void testLoggedoutByDefalut()
    {
        assertFalse(UserSession.isLoggedIn());
        assertFalse(UserSession.isAdmin());
        assertFalse(UserSession.isParticipant());
        assertNull(UserSession.getCurrentUser());
    }

    @Test 
    public void testAdminSessionAcess()
    {
        User admin = new User(1, "Admin User", "admin@conference.com", "hashed", 25, "ADMIN");
        UserSession.setCurretUser(admin);

        assertTrue(UserSession.isLoggedIn());
        assertTrue(UserSession.isAdmin());
        assertFalse(UserSession.isParticipant());
    }

    @Test 
    public void testParticipantSessionRestrictedFromAdmin()
    {
        User participant = new User(2, "Participant User", "user@conference.com", "hashed", 20, "PARTICIPANT");
        UserSession.setCurretUser(participant);

        assertTrue(UserSession.isLoggedIn());
        assertTrue(UserSession.isParticipant());
        assertFalse(UserSession.isAdmin());
    }

    @Test 
    public void testLogOutClearSession()
    {
        User admin = new User(1, "Admin User", "admin@conference.com", "hashed", 25, "ADMIN");
        UserSession.setCurretUser(admin);
        UserSession.clearSession();

        assertFalse(UserSession.isLoggedIn());
        assertFalse(UserSession.isAdmin());
        assertNull(UserSession.getCurrentUser());
    }

}
