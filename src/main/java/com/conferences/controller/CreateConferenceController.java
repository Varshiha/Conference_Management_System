/**
 * Handles user interactions and validates input
 * when creating a new conference
 * 
 */
package com.conferences.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.SQLException;
import com.conferences.model.LocalizationManager;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.Conference;
import com.conferences.service.ConferenceServices;

public class CreateConferenceController {
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
    private Button createButton, cancelButton;
    private ConferenceServices service = new ConferenceServices();

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
        LocalDate today = LocalDate.now();
        startDatePicker.setValue(today);
        endDatePicker.setValue(today);
    }

    @FXML 
    public void createConference() throws Exception{
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
        Conference conference = new Conference(title, capacity, start, end, venue);
        ConferencesDAO db = new ConferencesDAO();
        try{
            if(db.addConference(conference)){
                service.alertMessage(LocalizationManager.get("conf.create.success"), Alert.AlertType.INFORMATION);
                Stage stage = (Stage) createButton.getScene().getWindow();
                stage.close();
            }
        }catch(SQLException e){
            service.alertMessage(LocalizationManager.get("conf.create.error") + " " + e.getMessage(), Alert.AlertType.ERROR);
        }
    } 

    @FXML 
    public void cancelConference(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

