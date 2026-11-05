package com.conferences.tests;
import com.conferences.model.Registration;
import com.conferences.model.RegistrationsDAO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.sql.SQLException;

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
    Registration r = rd.getRegistrationById(1);
    boolean result = rd.deleteRegistration(r.getRegistrationId());
    assertEquals(true, result);
}
}
