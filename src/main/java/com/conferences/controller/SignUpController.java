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

public class SignUpController implements Initializable
{
    @FXML
    private Label headingLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button signupButton;

    @FXML
    private Button switchToLoginButton;

    private final UserDAO userDAO = new UserDAO();
    private final Map<String, Locale> languageMap = new LinkedHashMap<>();
    private boolean updatingLanguageCombo = false;

    

    @Override 
    public void initialize(URL location, ResourceBundle resources) 
    {
        languageMap.put("English",Locale.ENGLISH);
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
        headingLabel.setText(LocalizationManager.get("signup.heading"));
        languageLabel.setText(LocalizationManager.get("lang.label"));
        fullNameField.setPromptText(LocalizationManager.get("signup.fullname.prompt"));
        ageField.setPromptText(LocalizationManager.get("signup.age.prompt"));
        emailField.setPromptText(LocalizationManager.get("signup.email.prompt"));
        passwordField.setPromptText(LocalizationManager.get("signup.password.prompt"));
        confirmPasswordField.setPromptText(LocalizationManager.get("signup.confirmPassword.prompt"));
        signupButton.setText(LocalizationManager.get("signup.button.submit"));
        switchToLoginButton.setText(LocalizationManager.get("signup.button.switchToLogin"));
    }

    @FXML 
    private void handleLanguageChange(ActionEvent event)
    {
        if(updatingLanguageCombo)
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
    private void handleSignUp(ActionEvent event)
    {
        String fullName = "";
        String ageText = "";
        String email = "";
        String password = "";
        String confirmPassword = "";

        if(fullNameField.getText() != null)
        {
            fullName = fullNameField.getText().trim();
        }

        if(ageField.getText() != null)
        {
            ageText = ageField.getText().trim();
        }

        if(emailField.getText() != null)
        {
            email = emailField.getText().trim();
        }

        if(passwordField.getText() != null)
        {
            password = passwordField.getText();
        }

        if(confirmPasswordField.getText() != null)
        {
            confirmPassword = confirmPasswordField.getText();
        }

        if(fullName.isEmpty() || ageText.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty())
        {
            errorLabel.setText(LocalizationManager.get("signup.error.empty"));
            return;
        }

        int age;

        try
        {
            age = Integer.parseInt(ageText);

            if(age<= 0 || age>100)
            {
                errorLabel.setText(LocalizationManager.get("signup.error.age"));
                return;
            }
        }
        catch(NumberFormatException e)
        {
            errorLabel.setText(LocalizationManager.get("signup.error.age"));
            return;
        }

        if(!password.equals(confirmPassword))
        {
            errorLabel.setText(LocalizationManager.get("signup.error.passwordMismatch"));
            return;
        }

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        if(atIndex <=0 || dotIndex <= atIndex+1 || dotIndex >= email.length()-1)
        {
            errorLabel.setText(LocalizationManager.get("signup.error.email"));
            return;
        }

        if(password.length()<6)
        {
            errorLabel.setText(LocalizationManager.get("signup.error.password"));
            return;
        }

        if(userDAO.emailExists(email))
        {
            errorLabel.setText(LocalizationManager.get("signup.error.emailExists"));
            return;
        }

        boolean created = userDAO.registerUser(fullName, email, password, age, "PARTICIPANT");

        if (created) 
        {
            switchScene(event, "/view/LoginView.fxml");    
        }
        else
        {
            errorLabel.setText(LocalizationManager.get("signup.error.emailExists"));
        }
    }

    @FXML 
    private void handleSwitchToLogin(ActionEvent event)
    {
        switchScene(event, "/view/LoginView.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath)
    {
        try
        {
            URL fxmlURL = getClass().getResource(fxmlPath);
            FXMLLoader loader = new FXMLLoader(fxmlURL,LocalizationManager.getBundle());
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();
            if(currentScene == null)
            {
                stage.setScene(new Scene(root));
            }
            else
            {
                currentScene.setRoot(root);
            }
            stage.setTitle(LocalizationManager.get("app.title"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
