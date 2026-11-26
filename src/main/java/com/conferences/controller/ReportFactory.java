package com.conferences.controller;

import com.conferences.model.LocalizationManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReportFactory {
    public static Report createReport(int type) throws Exception{
        String path;
        String title;

        switch(type){
            case 0 :
                path = "/view/reports/ConferenceOverview.fxml";
                title = "report.overview";
                break;
            case 1 :
                path = "/view/reports/ConferenceParticipant.fxml";
                title = "report.participants";
                break;
             case 2:
                path = "/view/reports/WaitList.fxml";
                title = "report.waitlist";
                break;
            case 3 :
                path = "/view/reports/ParticipantRegistered.fxml";
                title = "report.registration";
                break;
            case 4 :
                path = "/view/reports/ConferenceOccupancy.fxml";
                title = "report.occupancy";
                break;
            case 5 :
                path = "/view/reports/UnregisteredConference.fxml";
                title = "report.unregistered";
                break;
            default:
                return null;
        }

        FXMLLoader loader = new FXMLLoader(ReportFactory.class.getResource(path), LocalizationManager.getBundle());
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(LocalizationManager.get(title));
        stage.setScene(new Scene(root));
        //Get the controller
        Report report = loader.getController();
        report.setStage(stage);
        return report;
    }
}
