package com.conferences.controller;

import com.conferences.service.ConferenceServices;
import com.conferences.model.LocalizationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReportsController {
    @FXML 
    private ComboBox<String> reportsOptionComboBox;
    @FXML 
    private Button okButton;
    @FXML 
    private Button cancelButton;

    public void initialize(){
        reportsOptionComboBox.getItems().addAll(
            LocalizationManager.get("report.overview"),
            LocalizationManager.get("report.participants"),
            LocalizationManager.get("report.waitlist"),
            LocalizationManager.get("report.registration"),
            LocalizationManager.get("report.occupancy"),
            LocalizationManager.get("report.unregistered"));
       reportsOptionComboBox.getSelectionModel().selectFirst();
    }

    @FXML 
    public void okReport() throws Exception{
        int selectedReport = reportsOptionComboBox.getSelectionModel().getSelectedIndex();
        if(selectedReport == -1){
            ConferenceServices service = new ConferenceServices();
            service.alertMessage(LocalizationManager.get("report.alert.select"), Alert.AlertType.ERROR);
            return;
        }
        switch(selectedReport){
            case 0 :
                openConferenceOverview();
                break;
            case 1 :
                conferenceParticipantsReport();
                break;
             case 2:
                waitListReport();
                break;
            case 3 :
                participantRegisteredReport();
                break;
            case 4 :
                conferenceOccupancyReport();
                break;
            case 5 :
                unregisteredConferenceReport();
                break;
        }
    }

    public void openConferenceOverview() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reports/ConferenceOverview.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.overview"));
        stage.setScene(new Scene(root));
        stage.show();
        
    }
    
    public void conferenceParticipantsReport() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reports/ConferenceParticipant.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.participants"));
        stage.setScene(new Scene(root));
        stage.show();
    }

     public void waitListReport() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reports/WaitList.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.waitlist"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void participantRegisteredReport() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reports/ParticipantRegistered.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.registration"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void conferenceOccupancyReport() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reports/ConferenceOccupancy.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.occupancy"));
        stage.setScene(new Scene(root));
        stage.show();
    }

     public void unregisteredConferenceReport() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reports/UnregisteredConference.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.unregistered"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML 
    public void cancelReport(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
