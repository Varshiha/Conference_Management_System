package com.conferences.tests;
import com.conferences.model.Conference;
import com.conferences.model.ConferencesDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.sql.SQLException;


public class ConferencesDAOTest {
    @Test
    public void testAddConference() throws SQLException {
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference(0, "Computer Science", 30, LocalDateTime.of(2026, 11, 2, 9, 0), LocalDateTime.of(2026, 11, 2, 12, 0), "Laval");
        cd.addConference(c);

        Conference conference = cd.getConferenceByID(1);
        assertEquals("Computer Science", conference.getTitle());
    }

    @Test 
    public void testDeleteConference() throws SQLException{
        ConferencesDAO cd = new ConferencesDAO();
        Conference c = new Conference(0, "Java Course", 20, LocalDateTime.of(2026, 12, 6, 1, 0), LocalDateTime.of(2026, 12, 6, 3, 0), "Montreal");
        cd.addConference(c);
        cd.deleteConference(1);
        assertEquals(null, cd.getConferenceByID(1));
    }
}
