/**
 * Represents a conference with its title, capacity,
 * schedule, location and registration information.
 * 
 */
package com.conferences.model;
import java.time.LocalDateTime;

public class Conference {

    private int conferenceID;
    private String title;
    private int capacity;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;

    private int confirmed;
    private int waitlisted;
    private int available;
    private boolean full;
    private String fullStatus;

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
     public Conference(String title, int capacity, LocalDateTime startDateTime, LocalDateTime endDateTime, String location, int confirmed, int waitlisted, int available,  boolean full) {
        this.title = title;
        this.capacity = capacity;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.confirmed=confirmed;
        this.waitlisted=waitlisted;
        this.available = available;
        this.full=full;
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
   public void setConfirmed(int confirmed){
        this.confirmed=confirmed;
    }
    public void setWaitlisted(int waitlisted){
        this.waitlisted=waitlisted;
    }
    public void setAvailable(int available){
        this.available=available;
    }
    public void setFull(boolean full){
        this.full=full;
    }
    public void setFullStatus(String fullStatus){
        this.fullStatus=fullStatus;
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
    public int getConfirmed(){
        return confirmed;
    }
    public int getWaitlisted(){
        return waitlisted;
    }
    public int getAvailable(){
        return available;
    }
    public boolean getFull(){
        return full;
    }
    public String getFullStatus(){
        return fullStatus;
    }
}
