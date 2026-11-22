package com.conferences.controller;
import java.sql.SQLException;
import java.util.List;

import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.Conference;
import com.conferences.model.UserSession;
import com.conferences.service.ConferenceServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
 

public class AdminPanelController {

    @FXML 
    private TableView<Conference> conferenceTableView;
    @FXML 
    private TableColumn<Conference, Integer> idColumn;
    @FXML 
    private TableColumn<Conference, String> titleColumn; 
    @FXML 
    private TableColumn<Conference, Integer> capacityColumn; 
    @FXML 
    private TableColumn<Conference, String> startColumn; 
    @FXML 
    private TableColumn<Conference, String> endColumn;
    @FXML 
    private TableColumn<Conference, String> venueColumn;
    @FXML 
    private TableColumn<Conference, Integer> confirmedColumn; 
    @FXML 
    private TableColumn<Conference, Integer> waitlistedColumn; 
    @FXML 
    private TableColumn<Conference, Integer> availableColumn;
    @FXML 
    private TableColumn<Conference, Boolean> fullColumn;  
    @FXML 
    private Label welcomeLabel;
    @FXML 
    private Button createButton;
    @FXML 
    private Button editButton;
    @FXML 
    private Button deleteButton;
    @FXML 
    private Button refreshButton;
    @FXML 
    private Button reportsButton;
    @FXML 
    private Button logoutButton;
    @FXML
    private SplitMenuButton englishSplitMenu;
    @FXML
    private MenuItem englishMenuItem;
    @FXML
    private MenuItem frenchMenuItem;

    private ConferencesDAO db = new ConferencesDAO();
    private ConferenceServices service = new ConferenceServices();


    @FXML 
    public void initialize() throws SQLException{

        //this is a check to make sure only ADMIN can acess this
        if(!UserSession.isAdmin())
        {
            //please redirect this back to the loginpage with an error
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("conferenceID"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
		endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
		venueColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		List<Conference> conferences = db.getAllConferences();
		conferenceTableView.getItems().setAll(conferences);	
    }

    @FXML
    private void createConferencePage() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateConference.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Create Conference");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void editConferencePage()  throws Exception{
        Conference selectedConference = conferenceTableView.getSelectionModel().getSelectedItem();
        if(selectedConference == null){
            service.alertMessage("Please select a conference to edit", Alert.AlertType.ERROR);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditConference.fxml"));
        Parent root = loader.load();
        EditConferenceController editController = loader.getController();
        editController.setConference(selectedConference);
        Stage stage = new Stage();
        stage.setTitle("Edit Conference");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void deleteConferencePage() throws SQLException{
        Conference selectedConference = conferenceTableView.getSelectionModel().getSelectedItem();
        if(selectedConference == null){
            service.alertMessage("Please select a conference to delete", Alert.AlertType.ERROR);
            return;
        }
        try {
            String error = service.deleteConferenceValidation(selectedConference.getConferenceID());
            if(error != null){
                service.alertMessage(error, Alert.AlertType.ERROR);
                return;
            }
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete conference");
            confirmation.setHeaderText("Are you sure you want to delete this conference?");
            confirmation.showAndWait();
            if(confirmation.getResult() == ButtonType.OK){
                boolean deleted = db.deleteConference(selectedConference.getConferenceID());
                if(deleted){
                    service.alertMessage("Conference deleted successfully!", Alert.AlertType.INFORMATION);
                    refreshConferencePage();
                }else{
                    service.alertMessage("Conference could not be deleted", Alert.AlertType.ERROR);
                }
            }
        }catch(SQLException e){
                    service.alertMessage("Error deleting conference: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void refreshConferencePage() throws SQLException{
        List<Conference> conferences = db.getAllConferences();
		conferenceTableView.getItems().setAll(conferences);	
    }

    @FXML
    private void reportsPage() {

    }
    @FXML
    private void englishLanguage() {

    }

    @FXML
    private void frenchLanguage() {

    }

    @FXML
    private void goToLogPage() 
    {
        //this basically sets the current user to null
        //check UserSession for more info
        //u just need to add the switch back to log part

        UserSession.clearSession();
    }
}
