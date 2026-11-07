package com.conferences.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.conferences.model.Registration;
import com.conferences.model.RegistrationsDAO;

public class RegistrationsDAOTest {

    @Test 
public void testAddRegistrations() throws SQLException{
    RegistrationsDAO rd = new RegistrationsDAO();
    Registration r = new Registration(1, 2,  LocalDateTime.of(2026, 11, 5, 9, 0), "CONFIRMED", null);
    boolean result = rd.addRegistration(r);
    assertEquals(true, result);
}

@Test 
public void testdeleteRegistrations() throws SQLException{
    RegistrationsDAO rd = new RegistrationsDAO();
    Registration r = rd.getRegistrationById(5);
    boolean result = rd.deleteRegistration(r.getRegistrationId());
    assertEquals(true, result);
}

@Test 
public void testGetRegistrationById() throws SQLException{
    RegistrationsDAO rd = new RegistrationsDAO();
   // Registration r = new Registration(3, 3,  LocalDateTime.of(2026, 11, 5, 16, 0), "CONFIRMED", null);
   // rd.addRegistration(r);
    Registration r1 = rd.getRegistrationById(6);
    assertEquals("CONFIRMED", r1.getStatus());
}

@Test 
public void testGetRegistrationsByUser() throws SQLException{
    RegistrationsDAO rd = new RegistrationsDAO();
    //Already added the first time I ran it, 
   // Registration r = new Registration(4, 3,  LocalDateTime.of(2026, 11, 5, 16, 0), "CONFIRMED", null);
   // rd.addRegistration(r);
    List<Registration> usersReg = rd.getRegistrationsByUser(3);
    assertEquals(3, usersReg.size());
}

@Test 
public void testGetRegistrationsByConference() throws SQLException{
    RegistrationsDAO rd = new RegistrationsDAO();
    Registration r = new Registration(4, 2,  LocalDateTime.of(2026, 11, 5, 16, 0), "CONFIRMED", null);
    rd.addRegistration(r);
    List<Registration> conferencesReg = rd.getRegistrationsByConference(r.getConferenceId());
    assertEquals(3, conferencesReg.size());
}

@Test 
public void testUpdateRegistration() throws SQLException{
    RegistrationsDAO rd = new RegistrationsDAO();
    Registration r = new Registration(10,1,  LocalDateTime.of(2026, 11, 5, 16, 0), "CONFIRMED", null);
    rd.addRegistration(r);
    r.setStatus("CANCELLED");
    rd.updateRegistration(r);
    Registration testReg = rd.getRegistrationById(r.getRegistrationId());
    assertEquals("CANCELLED", testReg.getStatus());
}
}
