package com.conferences.controller;

import com.conferences.DAO.UserDAO;
import com.conferences.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController implements Initializable
{
    @FXML
    private Label headingLabel;

    @FXML
    private Label subheadingLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button switchToSignupButton;

    private final UserDAO userDAO = new UserDAO();
    private final Map<String,Locale> languageMap = new LinkedHashMap<>();
    private boolean updatingLanguageCombo = false;



    
    @Override 
    public void initialize(URL location, ResourceBundle resouces) 
    {
        // Hint: initialize() will be called when the associated FXML has been completely loaded.
        languageMap.put("English", Locale.ENGLISH);
        languageMap.put("Français", Locale.FRENCH);

        languageComboBox.getItems().setAll(languageMap.keySet());
        syncLanguageComboSelection();
        applyLocalization();

    }

    private void syncLanguageComboSelection()
    {
        updatingLanguageCombo = true;
        if(Locale.FRENCH.getLanguage().equals(LocalizationManager.getLocale().getLanguage()))
        {
            languageComboBox.setValue("Français");
        }
        else
        {
          languageComboBox.setValue("English");
        }

        updatingLanguageCombo = false;
    }

    private void applyLocalization()
    {
        headingLabel.setText(LocalizationManager.get("login.heading"));
        subheadingLabel.setText(LocalizationManager.get("login.subheading"));
        languageLabel.setText(LocalizationManager.get("lang.label"));
        emailField.setPromptText(LocalizationManager.get("login.email.prompt"));
        passwordField.setPromptText(LocalizationManager.get("login.password.prompt"));
        loginButton.setText(LocalizationManager.get("login.button.submit"));
        switchToSignupButton.setText(LocalizationManager.get("login.button.switchToSignup"));
    }

    @FXML 
    private void handleLanguageChange(ActionEvent event)
    {
        if (updatingLanguageCombo)
        {
            return;
        }
        String selected = languageComboBox.getValue();
        Locale chosen = languageMap.getOrDefault(selected,Locale.ENGLISH);
        LocalizationManager.setLocale(chosen);
        applyLocalization();
        errorLabel.setText("");
    }

    @FXML
    private void handleLogin(ActionEvent event)
    {
        errorLabel.setText("");
        String email = "";
        if(emailField.getText() != null)
        {
            email = emailField.getText().trim();
        }

        String password ="";
        if(passwordField.getText() != null)
        {
            password = passwordField.getText().trim();
        }

        if(email.isEmpty() ||password.isEmpty())
        {
            errorLabel.setText(LocalizationManager.get("login.error.empty"));
            return;
        }

        User authenticatedUser = userDAO.authenticate(email, password);
        
        if(authenticatedUser == null)
        {
            errorLabel.setText(LocalizationManager.get("login.error.invalid"));
            return;
        }
        System.out.println("Logged in as: "+authenticatedUser.getFullName()+" ("+authenticatedUser.getRole()+")");

        if (authenticatedUser.getRole().equals("ADMIN")){   
            switchScene(event, "/view/AdminPanel.fxml");
        }   
    }

    @FXML 
    private void handleSwitchToSignup(ActionEvent event)
    {
        switchScene(event,"/view/SignUpView.fxml");
    }

    @FXML 
    private void switchScene(ActionEvent event,String fxmlPath)
    {
        try
        {
            URL fxmlURL = getClass().getResource(fxmlPath);
            FXMLLoader loader = new FXMLLoader(fxmlURL, LocalizationManager.getBundle());
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            /*if(currentScene == null)
            {
                stage.setScene(new Scene(root));
            }
            else
            {
                currentScene.setRoot(root);
            }*/

                stage.setScene(new Scene(root));
                stage.sizeToScene();
                stage.centerOnScreen();
            stage.setTitle(LocalizationManager.get("app.title"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
