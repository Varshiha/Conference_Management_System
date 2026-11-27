/**
 * Defines the common operations required by report
 * controller to manage and display their windows.
 * 
 */
package com.conferences.controller;
import javafx.stage.Stage;
public interface Report {
    void setStage(Stage stage);
    void showReport();
}
