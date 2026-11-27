/**
 * Displays the number and percentage of occupied
 * seats in a selected conference.
 * 
 */
package com.conferences.controller;
import java.sql.SQLException;
import java.util.List;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.*;
import com.conferences.service.ConferenceServices;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.text.NumberFormat;

public class ConferenceOccupancyController implements Report{
     @FXML 
    private ComboBox<String> conferenceComboBox;
    @FXML 
    private TextField numberTextField;
     @FXML 
    private TextField percentageTextField;
    @FXML 
    private Button viewButton;
    @FXML 
    private Button closeCOcButton;
    private ConferencesDAO db = new ConferencesDAO();
    private ConferenceServices service = new ConferenceServices();
    private List<Conference> conferences;
    private int selectedIndex;
    private Conference selectedConference;
    private Stage reportStage;

    @FXML 
    public void initialize() throws SQLException{
        conferences = db.getAllConferences();
        for(Conference c : conferences){
            conferenceComboBox.getItems().add(c.getTitle());
        }
        conferenceComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void viewOccupancy() throws SQLException{
        selectedIndex = conferenceComboBox.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1){
            service.alertMessage(LocalizationManager.get("report.alert.conference.select"), Alert.AlertType.ERROR);
            return;
        }
        selectedConference = conferences.get(selectedIndex);
        int conferenceId = selectedConference.getConferenceID();
        int capacity = selectedConference.getCapacity();
        int occupied = service.getConfirmedCount(conferenceId);

        double percentage = (double) occupied/capacity *100;
        numberTextField.setText(String.valueOf(occupied));

        NumberFormat format = NumberFormat.getPercentInstance(LocalizationManager.getLocale());
        format.setMaximumFractionDigits(2);
        percentageTextField.setText(format.format(percentage/100));
    }
    
    @FXML 
    public void closeConferenceOccupancy(){
        Stage stage = (Stage) closeCOcButton.getScene().getWindow();
        stage.close();
    }

    @Override 
    public void setStage(Stage stage){
        this.reportStage = stage;
    }
    @Override public void showReport(){
        reportStage.show();
    }
}
