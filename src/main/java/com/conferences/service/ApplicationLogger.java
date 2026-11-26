package com.conferences.service;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ApplicationLogger {

    //Store the single instance
    private static ApplicationLogger instance;
    private final Logger logger = Logger.getLogger("ConferenceManagementSystem");
    
    //other classes cant create other instances
    private ApplicationLogger(){}

    public static synchronized ApplicationLogger getInstance(){
        if(instance == null){
            instance = new ApplicationLogger();
        }
        return instance;
    }

    public void log(Level level, String message, Throwable e){
        logger.log(level, message, e);
    }
    
}
