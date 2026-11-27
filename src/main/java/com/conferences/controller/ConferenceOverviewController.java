/**
 * Display conferenc details, capacity, confirmed 
 * registrations and available seats.
 */
package com.conferences.controller;
import java.sql.SQLException;
import java.util.List;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.Conference;
import com.conferences.service.ConferenceServices;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.conferences.model.LocalizationManager;

public class ConferenceOverviewController implements Report{
    @FXML 
    private TableView<Conference> conferenceTableView;
    @FXML 
    private TableColumn<Conference, Integer> idColumn;
    @FXML 
    private TableColumn<Conference, String> titleColumn; 
    @FXML 
    private TableColumn<Conference, Integer> capacityColumn; 
    @FXML 
    private TableColumn<Conference, Integer> confirmedColumn; 
    @FXML 
    private TableColumn<Conference, Integer> availableColumn;
    @FXML 
    private Button closeCOButton;
    private ConferencesDAO db = new ConferencesDAO();
    private ConferenceServices service = new ConferenceServices();
    private Stage reportStage;

    @FXML 
    public void initialize() throws SQLException{
        idColumn.setCellValueFactory(new PropertyValueFactory<>("conferenceID"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        confirmedColumn.setCellValueFactory(new PropertyValueFactory<>("confirmed"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

		List<Conference> conferences = db.getAllConferences();
        for(Conference c : conferences){
            int id = c.getConferenceID();
            c.setConfirmed(service.getConfirmedCount(id));
            c.setWaitlisted(service.getWaitlistedCount(id));
            c.setAvailable(service.getAvailableSeats(id, c.getCapacity()));
            c.setFull(service.isConferenceFull(id, c.getCapacity()));
        }
		conferenceTableView.getItems().setAll(conferences);	
        conferenceTableView.setPlaceholder(new Label(LocalizationManager.get("report.empty")));
    }
    @FXML 
    public void closeConferenceOverview(){
        Stage stage = (Stage) closeCOButton.getScene().getWindow();
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
