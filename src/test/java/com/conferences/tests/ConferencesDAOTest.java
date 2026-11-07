package com.conferences.tests;
import com.conferences.model.Conference;
import com.conferences.model.ConferencesDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.List;



public class ConferencesDAOTest {
   
    @Test
    public void testAddConference() throws SQLException {
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference("Java Workshop", 15, LocalDateTime.of(2026, 12, 2, 9, 0), LocalDateTime.of(2026, 12, 2, 12, 0), "Laval");
        boolean result = cd.addConference(c);
        assertEquals(true, result);
    }

    @Test 
    public void testDeleteConference() throws SQLException{
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference("C++ Course", 2, LocalDateTime.of(2026, 12, 6, 1, 0), LocalDateTime.of(2026, 12, 6, 3, 0), "Montreal");
        cd.addConference(c);
        cd.deleteConference(c.getConferenceID());
        assertEquals(null, cd.getConferenceByID(c.getConferenceID()));
    }

    @Test 
    public void testGetConferenceByID() throws SQLException{
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference("HTML Conference", 4, LocalDateTime.of(2026, 12, 6, 1, 0), LocalDateTime.of(2026, 12, 6, 3, 0), "Montreal");
        cd.addConference(c);
        Conference result = cd.getConferenceByID((c.getConferenceID()));
        assertEquals("HTML Conference", result.getTitle());
    }

    @Test 
    public void testGetAllConferences() throws SQLException{
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference("Python Course", 3, LocalDateTime.of(2026, 12, 6, 1, 0), LocalDateTime.of(2026, 12, 6, 3, 0), "Montreal");
        cd.addConference(c);
        List<Conference> conferences = cd.getAllConferences();
        assertEquals(false, conferences.isEmpty());
    }
    @Test 
    public void testUpdateConference() throws SQLException{
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference("JavaScript Course", 20, LocalDateTime.of(2026, 12, 6, 1, 0), LocalDateTime.of(2026, 12, 6, 3, 0), "Montreal");
        cd.addConference(c);
        c.setTitle("Advanced JavaScript Course");
        boolean result = cd.updateConference(c);
        Conference c1 = cd.getConferenceByID((c.getConferenceID()));
        assertEquals(true, result);
        assertEquals("Advanced JavaScript Course", c1.getTitle());

    }

        

}
