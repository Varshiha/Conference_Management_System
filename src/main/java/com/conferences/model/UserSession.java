package com.conferences.model;

public class UserSession 
{
    private static User currentUser = null;
    
    private UserSession()
    {

    }

    public static  void setCurretUser(User user)
    {
        currentUser = user;
    }

    public static User getCurrentUser()
    {
        return  currentUser;
    }

    public static void clearSession()
    {
        currentUser = null;
    }

    public static boolean isLoggedIn()
    {
        if(currentUser != null)
        {
            return true;
        }
        return false;
    }

    public static boolean isAdmin()
    {
        if(currentUser == null)
        {
            return false;
        }
        if("ADMIN".equalsIgnoreCase(currentUser.getRole()))
        {
            return true;
        }
        return false;
    }

    public static boolean isParticipant()
    {
        if(currentUser == null)
        {
            return false;
        }
        if("PARTICIPANT".equalsIgnoreCase(currentUser.getRole()))
        {
            return true;
        }
        return false;
    }
}
