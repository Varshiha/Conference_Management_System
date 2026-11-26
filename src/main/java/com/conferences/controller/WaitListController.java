package com.conferences.controller;
import java.sql.SQLException;
import java.util.List;
import com.conferences.model.Conference;
import com.conferences.model.LocalizationManager;
import com.conferences.model.User;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.service.ConferenceServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class WaitListController {
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
    private Button closeWButton;
    private ConferencesDAO db = new ConferencesDAO();
    private ConferenceServices service = new ConferenceServices();
    private List<Conference> conferences;
    private int selectedIndex;
    private Conference selectedConference;

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
        List<User> participants = service.getWaitlistedParticipants(conferenceId);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        participantTableView.getItems().setAll(participants);	
    }

    @FXML 
    public void closeWaitListReport(){
        Stage stage = (Stage) closeWButton.getScene().getWindow();
        stage.close();
    }
}
