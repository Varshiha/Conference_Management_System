/**
 * Displays the confirmed participants of a selected conference
 * 
 */
package com.conferences.controller;
import java.sql.SQLException;
import java.util.List;
import com.conferences.model.LocalizationManager;
import com.conferences.model.Conference;
import com.conferences.model.User;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.service.ConferenceServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ConferenceParticipantController implements Report{
    @FXML 
    private ComboBox<String> conferenceComboBox;
    @FXML
    private TableView<User> participantTableView;
    @FXML 
    private TableColumn<User, Integer> idColumn;
    @FXML 
    private TableColumn<User, String> fullNameColumn;
    @FXML 
    private Button viewButton;
    @FXML 
    private Button closeCPButton;
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
        participantTableView.setPlaceholder(new Label(LocalizationManager.get("report.empty")));  
    }
    
    @FXML
    public void viewParticipantTable() throws SQLException{
        selectedIndex = conferenceComboBox.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1){
            service.alertMessage(LocalizationManager.get("report.alert.conference.select"), Alert.AlertType.ERROR);
            return;
        }
        selectedConference = conferences.get(selectedIndex);
        int conferenceId = selectedConference.getConferenceID();
        List<User> participants = service.getConfirmedParticipants(conferenceId);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        participantTableView.getItems().setAll(participants);	
    }

    @FXML 
    public void closeConferenceParticipant(){
        Stage stage = (Stage) closeCPButton.getScene().getWindow();
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
