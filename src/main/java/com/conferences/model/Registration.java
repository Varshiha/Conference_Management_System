package com.conferences.model;
import java.time.LocalDateTime;

public class Registration {
    private int registrationId;
    private int conferenceId;
    private int userId;
    private LocalDateTime registrationDate;
    private String status;
    private Integer waitinglistPosition;

    public Registration(int registrationId, int conferenceId, int userId, LocalDateTime registrationDate, String status, Integer waitinglistPosition) {
        this.registrationId = registrationId;
        this.conferenceId = conferenceId;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.waitinglistPosition = waitinglistPosition;
    }

    public Registration(int conferenceId, int userId, LocalDateTime registrationDate, String status, Integer waitinglistPosition) {
        this.conferenceId = conferenceId;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.waitinglistPosition = waitinglistPosition;
    }

    public int getRegistrationId() {
        return registrationId;
    }
    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }
    public int getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getWaitinglistPosition() {
        return waitinglistPosition;
    }
    public void setWaitinglistPosition(Integer waitinglistPosition) {
        this.waitinglistPosition = waitinglistPosition;
    }

}
