package com.conferences.controllers;

import com.conferences.model.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addUserController {
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private TextField txtAge;
    @FXML private TextField txtRole;

    UserDAO uDAO = new UserDAO();

	
	@FXML
	private void onAddSave()
	{
        String fullname = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        int age = (int) Integer.parseInt(txtAge.getText());
        String role = txtRole.getText();

		uDAO.registerUser(fullname, email, password, age, role);
	}
	
	@FXML
	private void onCancel()
	{
		closeWindow();
	}
	
	private void closeWindow()
	{
		Stage stage = (Stage) txtName.getScene().getWindow();
		stage.close();
	}
}
