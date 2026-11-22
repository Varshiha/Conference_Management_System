package com.conferences.tests;
import com.conferences.model.LocalizationManager;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocalizationTes 
{
    @Test 
    public void testSwitchToEnglishAndFrench()
    {
        LocalizationManager.setLocale(Locale.ENGLISH);
        String enTitle = LocalizationManager.get("app.title");
        String enInvalid = LocalizationManager.get("login.error.invalid");
        assertNotNull(enTitle);
        assertFalse(enTitle.isEmpty());
        assertNotNull(enInvalid);
        assertFalse(enInvalid.isEmpty());

        LocalizationManager.setLocale(Locale.FRENCH);
        String frTitle = LocalizationManager.get("app.title");
        String frInvalid = LocalizationManager.get("login.error.invalid");
        assertNotNull(frTitle);
        assertFalse(frTitle.isEmpty());
        assertNotNull(frInvalid);
        assertFalse(frInvalid.isEmpty());
    }
}
