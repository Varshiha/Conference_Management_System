/**
 * Manages the administrator interface and handles
 * conference management and report access.
 * 
 */
package com.conferences.controller;
import java.util.logging.Level;
import com.conferences.service.ApplicationLogger;
import javafx.application.Platform;
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
import com.conferences.model.LocalizationManager;
import java.util.Locale;

public class AdminPanelController{
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
    private TableColumn<Conference, String> fullColumn;  
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
    @FXML
    private Label conferenceBannerLabel;
    @FXML
    private Label adminHeadingLabel;
    @FXML
    private Label languageLabel;
    private static final ApplicationLogger logger = ApplicationLogger.getInstance();
    private ConferencesDAO db = new ConferencesDAO();
    private ConferenceServices service = new ConferenceServices();

    @FXML 
    public void initialize() throws SQLException{
        conferenceTableView.setPlaceholder(new Label(LocalizationManager.get("report.empty")));
        if(!UserSession.isAdmin())
        {
            Platform.runLater(() -> {
                service.alertMessage(LocalizationManager.get("alert.unauthorized.admin"), Alert.AlertType.ERROR);
                try{
                    goToLogPage();

                }catch(Exception e){
                    logger.log(Level.SEVERE, "Error redirecting to login page", e);
                }
            });
            return;
        }
        welcomeLabel.setText(LocalizationManager.get("admin.welcome")+ " " + UserSession.getCurrentUser().getFullName());        
		
        if (LocalizationManager.getLocale().getLanguage().equals("fr")) {
            englishSplitMenu.setText("Français");
        } else {
            englishSplitMenu.setText("English");
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("conferenceID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
		endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
		venueColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		confirmedColumn.setCellValueFactory(new PropertyValueFactory<>("confirmed"));
        waitlistedColumn.setCellValueFactory(new PropertyValueFactory<>("waitlisted"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
		fullColumn.setCellValueFactory(new PropertyValueFactory<>("fullStatus"));

		List<Conference> conferences = db.getAllConferences();
        for(Conference c : conferences){
            int id = c.getConferenceID();
            c.setConfirmed(service.getConfirmedCount(id));
            c.setWaitlisted(service.getWaitlistedCount(id));
            c.setAvailable(service.getAvailableSeats(id, c.getCapacity()));
            boolean full = service.isConferenceFull(id, c.getCapacity());
            c.setFull(full);
            if(full){
                c.setFullStatus(LocalizationManager.get("admin.full.true"));
            }else{
                c.setFullStatus(LocalizationManager.get("admin.full.false"));
            }
        }
		conferenceTableView.getItems().setAll(conferences);	
    }

    @FXML
    private void createConferencePage() throws Exception{
        if(!checkAdmin()){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateConference.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("conf.create.heading"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void editConferencePage()  throws Exception{
        if(!checkAdmin()){
            return;
        }
        Conference selectedConference = conferenceTableView.getSelectionModel().getSelectedItem();
        if(selectedConference == null){
            service.alertMessage(LocalizationManager.get("admin.alert.edit.select"), Alert.AlertType.ERROR);
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditConference.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        EditConferenceController editController = loader.getController();
        editController.setConference(selectedConference);
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("conf.edit.heading"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void deleteConferencePage() throws SQLException{
        if(!checkAdmin()){
            return;
        }
        Conference selectedConference = conferenceTableView.getSelectionModel().getSelectedItem();
        if(selectedConference == null){
            service.alertMessage(LocalizationManager.get("admin.alert.delete.select"), Alert.AlertType.ERROR);
            return;
        }
        try {
            String error = service.deleteConferenceValidation(selectedConference.getConferenceID());
            if(error != null){
                service.alertMessage(error, Alert.AlertType.ERROR);
                return;
            }
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(LocalizationManager.get("admin.alert.delete.title"));
            confirmation.setHeaderText(LocalizationManager.get("admin.alert.delete.confirm"));
            Button cancel = (Button) confirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText(LocalizationManager.get("dialog.button.cancel"));
            confirmation.showAndWait();
            if(confirmation.getResult() == ButtonType.OK){
                boolean deleted = db.deleteConference(selectedConference.getConferenceID());
                if(deleted){
                    service.alertMessage(LocalizationManager.get("admin.alert.delete.success"), Alert.AlertType.INFORMATION);
                    refreshConferencePage();
                }else{
                    service.alertMessage(LocalizationManager.get("admin.alert.delete.failed"), Alert.AlertType.ERROR);
                }
            }
        }catch(SQLException e){
                    service.alertMessage(LocalizationManager.get("admin.alert.delete.error") + " " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void refreshConferencePage() throws SQLException{
        if(!checkAdmin()){
            return;
        }
        List<Conference> conferences = db.getAllConferences();
        for(Conference c : conferences){
            int id = c.getConferenceID();
            c.setConfirmed(service.getConfirmedCount(id));
            c.setWaitlisted(service.getWaitlistedCount(id));
            c.setAvailable(service.getAvailableSeats(id, c.getCapacity()));
            boolean full = service.isConferenceFull(id, c.getCapacity());
            c.setFull(full);
            if(full){
                c.setFullStatus(LocalizationManager.get("admin.full.true"));
            }else{
                c.setFullStatus(LocalizationManager.get("admin.full.false"));
            }
        }
		conferenceTableView.getItems().setAll(conferences);	
    }

    @FXML
    private void reportsPage() throws Exception{
        if(!checkAdmin()){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Reports.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get("report.heading"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    private void englishLanguage() throws Exception{
        LocalizationManager.setLocale(Locale.ENGLISH);
        reloadAdminPanel();
    }

    @FXML
    private void frenchLanguage() throws Exception{
        LocalizationManager.setLocale(Locale.FRENCH);
        reloadAdminPanel();
    }

    private void reloadAdminPanel() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminPanel.fxml"), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = (Stage) englishSplitMenu.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    private void goToLogPage() throws Exception{
        UserSession.clearSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"),LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setTitle(LocalizationManager.get("app.title"));
    }

    //make sure user is admin and not participant
    private boolean checkAdmin(){
        if(!UserSession.isAdmin()){
            service.alertMessage(LocalizationManager.get("alert.unauthorized.admin"), Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
}
