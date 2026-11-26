package com.conferences.model;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ParticipantPanelController implements Initializable
{

    @FXML private TableView<Conference> tableConferences;
    @FXML private TableColumn<Conference, Integer> colId;
    @FXML private TableColumn<Conference, String> colTitle;
    @FXML private TableColumn<Conference, Integer> colCapacity;
    @FXML private TableColumn<Conference, LocalDateTime> colStartTime;
    @FXML private TableColumn<Conference, LocalDateTime> colEndTime;
    @FXML private TableColumn<Conference, String> colLocation;
    
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle bundle)
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("conferenceID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        try {
            loadData();
        } catch (SQLException e) {
            System.err.print(e);
        }
    }

    private void loadData() throws SQLException
    {
        ObservableList<Conference> dList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Conferences"; // Later: Use flags and case for options

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
            {
                while (rs.next())
                {
                    int id = rs.getInt("C_ID");
                    String title = rs.getString("Title");
                    int cap = rs.getInt("Capacity");
                    LocalDateTime startTime = rs.getObject("Start_DateTime", LocalDateTime.class);
                    LocalDateTime endTime = rs.getObject("End_DateTime", LocalDateTime.class);
                    String location = rs.getString("Location");

                    dList.add(new Conference(id, title, cap, startTime, endTime, location));
                }
                tableConferences.setItems(dList);
            } catch(SQLException e){
                throw e;
            }
    }


    //Methods
    @FXML
    @SuppressWarnings("unused")
    private void cancel(){
        Stage s = (Stage) tableConferences.getScene().getWindow();
        s.close();
    }

    @FXML
    private void register(){}

    @FXML
    private void listConfirmed(){}

    @FXML
    private void listWaitlisted(){}
    
    //TODO Add Language change method

}
