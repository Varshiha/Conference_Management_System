package com.conferences;

import java.sql.SQLException;

import com.conferences.model.DatabaseConnection;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection.getConnection();
        Application.launch(App.class, args);
    }
}