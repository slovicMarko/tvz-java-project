package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;


public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static String usernameString;
    private static String passwordString;

    private static final Logger logger = LoggerFactory.getLogger(LoginScreenController.class);


    public void initialize(){
        usernameField.setOnKeyReleased(event -> {
            usernameString = usernameField.getText();
        });

        passwordField.setOnKeyReleased(event -> {
            passwordString = passwordField.getText();
        });
    }

    public static void loginButtonClicked(){
        String loggingMessage = FileUtilUsers.getLoggedInUser(usernameString, User.hashPassword(passwordString));
        try{

            if (Optional.of(MainApplication.getActiveUser()).isPresent()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(loggingMessage);
                alert.setContentText("Uspješno ste se prijavili u aplikaciju.");
                alert.showAndWait();
                NavigationMethods.goToProjectSearchPage();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Neuspješno prijava!");
            alert.setContentText(loggingMessage);
            alert.showAndWait();
        }


    }


}
