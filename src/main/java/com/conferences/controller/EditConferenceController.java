package com.conferences.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.SQLException;

import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.*;
import com.conferences.service.ConferenceServices;

public class EditConferenceController {
    @FXML 
    private TextField titleTextField;
    @FXML 
    private TextField capacityTextField;
    @FXML 
    private TextField venueTextField;
    @FXML 
    private DatePicker startDatePicker;
    @FXML 
    private DatePicker endDatePicker;
    @FXML 
    private ComboBox<String> startHourComboBox;
    @FXML 
    private ComboBox<String> endHourComboBox;
    @FXML 
    private ComboBox<String> startMinuteComboBox;
    @FXML 
    private ComboBox<String> endMinuteComboBox;
    @FXML 
    private Button editButton, cancelButton;
    private ConferenceServices service = new ConferenceServices();
    private Conference selectedConference;

    @FXML 
    public void initialize(){
        for(int i = 0; i < 24; i++){
            String hour;
            if(i < 10){hour = "0" + i;}
            else {hour = "" + i;}
            startHourComboBox.getItems().add(hour);
            endHourComboBox.getItems().add(hour);
        }
        for(int i = 0; i < 60; i++){
            String minute;
            if(i < 10){minute = "0" + i;}
            else {minute = "" + i;}
            startMinuteComboBox.getItems().add(minute);
            endMinuteComboBox.getItems().add(minute);
        }
    }

    public void setConference(Conference conference){
        selectedConference = conference;
        titleTextField.setText(selectedConference.getTitle());
        capacityTextField.setText(String.valueOf(selectedConference.getCapacity()));
        venueTextField.setText(selectedConference.getLocation());
        startDatePicker.setValue(selectedConference.getStartDateTime().toLocalDate());
        endDatePicker.setValue(selectedConference.getEndDateTime().toLocalDate());
        startHourComboBox.setValue(String.format("%02d",selectedConference.getStartDateTime().getHour()));
        startMinuteComboBox.setValue(String.format("%02d",selectedConference.getStartDateTime().getMinute()));
        endHourComboBox.setValue(String.format("%02d",selectedConference.getEndDateTime().getHour()));
        endMinuteComboBox.setValue(String.format("%02d",selectedConference.getEndDateTime().getMinute())); 
    }

    @FXML 
    public void editConference() throws SQLException{
        String title = titleTextField.getText();
        String error = service.titleValidation(title);
        if(error != null){
            service.alertMessage(error, Alert.AlertType.ERROR);
            return;
        }
        String capacityText = capacityTextField.getText();
        error = service.capacityValidation(capacityText);
        if(error != null){
            service.alertMessage(error, Alert.AlertType.ERROR);
            return;
        }
        int capacity = Integer.parseInt(capacityText.trim());
        try{
            error = service.capacityUpdateValidation(selectedConference.getConferenceID(), capacity);
            if(error != null){
            service.alertMessage(error, Alert.AlertType.ERROR);
            return;
            }
        }catch(SQLException e){
            service.alertMessage(LocalizationManager.get("conf.edit.capacity.error") + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String sHour = startHourComboBox.getValue();
        String sMinute = startMinuteComboBox.getValue();
        String eHour = endHourComboBox.getValue();
        String eMinute = endMinuteComboBox.getValue();
        if(startDate == null || endDate == null || sHour == null || sMinute == null || eHour == null || eMinute == null){
            service.alertMessage(LocalizationManager.get("validation.date.empty"), Alert.AlertType.ERROR);
            return;
        }
        LocalDateTime start = startDate.atTime(Integer.parseInt(sHour),Integer.parseInt(sMinute));
        LocalDateTime end = endDate.atTime(Integer.parseInt(eHour),Integer.parseInt(eMinute));
        error = service.dateValidation(start,end);
        if(error != null){
            service.alertMessage(error, Alert.AlertType.ERROR);
            return;
        }
        String venue = venueTextField.getText();
        Conference conference = new Conference(selectedConference.getConferenceID(),title, capacity, start, end, venue);
        ConferencesDAO db = new ConferencesDAO();
        try{
            boolean updated = db.updateConference(conference);
            if(updated){
                service.alertMessage(LocalizationManager.get("conf.edit.success"), Alert.AlertType.INFORMATION);
                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.close();
            }else{
                service.alertMessage(LocalizationManager.get("conf.edit.notFound"), Alert.AlertType.ERROR);
            }
        }catch(SQLException e){
            service.alertMessage(LocalizationManager.get("conf.edit.error") + " " + e.getMessage(), Alert.AlertType.ERROR);
        }
    } 

    @FXML 
    public void cancelConference(){
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }
}
