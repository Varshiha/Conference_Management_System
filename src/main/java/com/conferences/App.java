package com.conferences;
import java.sql.SQLException;

import com.conferences.model.DatabaseConnection;
import com.conferences.model.LocalizationManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	@Override
	public void start(Stage pStage) throws Exception
	{
		FXMLLoader l = new FXMLLoader(getClass().getResource("/com/conferences/LoginView.fxml"), LocalizationManager.getBundle());
		Parent r = l.load();

		Scene scene = new Scene(r);
		pStage.setTitle(LocalizationManager.get("app.title"));
		pStage.setScene(scene);
		pStage.show();
	}
	public static void main(String[] args) throws SQLException {
		DatabaseConnection.getConnection();
		launch(args);
	}

}
