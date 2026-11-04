package com.conferences.model;
import java.time.LocalDateTime;

public class Conference {

    private int conferenceID;
    private String title;
    private int capacity;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;

    //Constructor
    public Conference(int conferenceID, String title, int capacity, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.conferenceID = conferenceID;
        this.title = title;
        this.capacity = capacity;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
    }
     public Conference(String title, int capacity, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.title = title;
        this.capacity = capacity;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
    }

    //Setters
    public void setConferenceID(int conferenceID) {
        this.conferenceID = conferenceID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    //Getters
    public int getConferenceID() {
        return conferenceID;
    }   
    public String getTitle() {
        return title;
    }
    public int getCapacity() {
        return capacity;
    }   
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }   
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }   
    public String getLocation() {
        return location;
    }   


}
