package com.conferences.controller;
import java.sql.SQLException;
import java.util.List;
import com.conferences.model.Conference;
import com.conferences.model.LocalizationManager;
import com.conferences.service.ConferenceServices;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UnregisteredConferenceController implements Report{
    @FXML 
    private TableView<Conference> conferenceTableView;
    @FXML 
    private TableColumn<Conference, Integer> idColumn;
    @FXML 
    private TableColumn<Conference, String> titleColumn; 
    @FXML 
    private Button closeUCButton;
    private ConferenceServices service = new ConferenceServices();
    private Stage reportStage;


    @FXML 
    public void initialize() throws SQLException{
        conferenceTableView.setPlaceholder(new Label(LocalizationManager.get("report.empty")));
       refreshConferenceTable();
    }

    @FXML 
    public void refreshConferenceTable() throws SQLException{
        idColumn.setCellValueFactory(new PropertyValueFactory<>("conferenceID"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		List<Conference> conferences = service.getUnregisteredConference();
		conferenceTableView.getItems().setAll(conferences);	
    }
    @FXML 
    public void closeUnregisteredConference(){
        Stage stage = (Stage) closeUCButton.getScene().getWindow();
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
