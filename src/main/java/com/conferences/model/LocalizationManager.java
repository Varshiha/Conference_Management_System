package com.conferences.model;

import java.util.Locale;
import java.util.ResourceBundle;

public  final class LocalizationManager 
{
    private static final String BUNDLE_BASE_NAME = "com.conference.messages";
    private static Locale currentLocale = Locale.ENGLISH;
    private static ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME,currentLocale);

    private LocalizationManager()
    {

    }

    public static void setLocale(Locale locale)
    {
        if(locale != null)
        {
            currentLocale = locale;
            bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME,currentLocale);
        }
    }

    public static Locale getLocale()
    {
        return currentLocale;
    }

    public static ResourceBundle getBundle()
    {
        return bundle;
    }

    public static String get(String key)
    {
        return bundle.getString(key);
    }
}
