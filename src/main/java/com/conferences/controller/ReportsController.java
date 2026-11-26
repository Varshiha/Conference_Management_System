package com.conferences.controller;
import com.conferences.service.ConferenceServices;
import com.conferences.model.LocalizationManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReportsController {
    @FXML 
    private ComboBox<String> reportsOptionComboBox;
    @FXML 
    private Button okButton;
    @FXML 
    private Button cancelButton;
    ConferenceServices service = new ConferenceServices();

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
        int selectedIndex = reportsOptionComboBox.getSelectionModel().getSelectedIndex();
        Report report = ReportFactory.createReport(selectedIndex);
        if(report == null){
            service.alertMessage(LocalizationManager.get("report.alert.select"), Alert.AlertType.ERROR);
            return; 
        }
        report.showReport();
    }

    @FXML 
    public void cancelReport(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}