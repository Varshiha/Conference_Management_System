package com.conferences.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.conferences.model.Conference;
import com.conferences.model.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ParticipantPanelController implements Initializable
{

    @FXML
    private TableView<Conference> tableConferences;

    @FXML
    private TableColumn<Conference, Integer> colId;

    @FXML
    private TableColumn<Conference, String> colTitle;

    @FXML
    private TableColumn<Conference, Integer> colCapacity;

    @FXML
    private TableColumn<Conference, LocalDateTime> colStartTime;

    @FXML
    private TableColumn<Conference, LocalDateTime> colEndTime;

    @FXML
    private TableColumn<Conference, String> colLocation;
    


    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle bundle)
    {
        //TODO
        if(!UserSession.isLoggedIn())
        {
            //pls do the same thing here swithc it back to log in page

            //and pls when u add the logout button its gonna have 
            // the same logic as the one in admin panel controller
        }
    }


    //Methods
    @FXML
    private void cancel(){}

    @FXML
    private void register(){}

    @FXML
    private void listConfirmed(){}

    @FXML
    private void listWaitlisted(){}
    
    //TODO Add Language change method

}
