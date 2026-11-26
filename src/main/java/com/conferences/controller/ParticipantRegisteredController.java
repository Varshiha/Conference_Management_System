package com.conferences.controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.conferences.DAO.UserDAO;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.Conference;
import com.conferences.model.Registration;
import com.conferences.model.User;
import com.conferences.service.ConferenceServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import com.conferences.model.LocalizationManager;

public class ParticipantRegisteredController {
    @FXML 
    private ComboBox<String> participantComboBox;
    @FXML
    private TableView<Conference> conferenceTableView;
    @FXML 
    private TableColumn<Conference, Integer> idColumn;
    @FXML
    private TableColumn<Conference, String> titleColumn;
    @FXML 
    private TableColumn<Conference, String> statusColumn;
    @FXML 
    private Button viewButton;
    @FXML 
    private Button closePRButton;
    private UserDAO udb = new UserDAO();
    private ConferencesDAO cdb = new ConferencesDAO();
    private ConferenceServices service = new ConferenceServices();
    private List<User> participants;
    private int selectedIndex;
    private User selectedParticipant;

    @FXML 
    public void initialize() throws SQLException{
        participants = udb.getAllParticipants();
        for(User p : participants){
            participantComboBox.getItems().add(p.getFullName());
        }        
        participantComboBox.getSelectionModel().selectFirst();
        conferenceTableView.setPlaceholder(new Label(LocalizationManager.get("report.empty")));
    }
    @FXML
    public void viewConferenceTable() throws SQLException{
        selectedIndex = participantComboBox.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1){
            service.alertMessage(LocalizationManager.get("report.alert.participants.select"), Alert.AlertType.ERROR);
            return;
        }
        selectedParticipant = participants.get(selectedIndex);
        int participantsId = selectedParticipant.getId();
        List<Registration> registrations = service.getParticipantRegistrations(participantsId);
        HashMap<Integer, String> registrationMap = new HashMap<>();
        for(Registration r : registrations){
            registrationMap.put(r.getConferenceId(), r.getStatus());
        }
        List<Conference> conferences = new ArrayList<>();
        for(Registration r : registrations){
            Conference c = cdb.getConferenceByID(r.getConferenceId());
            if(c != null){
                conferences.add(c);
            }
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("conferenceID"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        statusColumn.setCellValueFactory(cellData -> {
            Conference c = cellData.getValue();
            String status = registrationMap.get(c.getConferenceID());
            if(status.equals("CONFIRMED")){
                status = LocalizationManager.get("report.status.confirmed");
            }else if(status.equals("WAITLISTED")){
                status = LocalizationManager.get("report.status.waitlisted");
            }else if(status.equals("CANCELLED")){
                status = LocalizationManager.get("report.status.cancelled");
            }
            return new SimpleStringProperty(status);
        });
        conferenceTableView.getItems().setAll(conferences);	
    }

    @FXML 
    public void closeParticipantRegistered(){
        Stage stage = (Stage) closePRButton.getScene().getWindow();
        stage.close();
    }
}
