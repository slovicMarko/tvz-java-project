package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;


public class LoginScreenController {
    private static final Logger logger = LoggerFactory.getLogger(LoginScreenController.class);

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private static String usernameString = "";
    private static String passwordString = "";

    public void initialize(){
        usernameField.setOnKeyReleased(event -> {
            usernameString = usernameField.getText();
        });

        passwordField.setOnKeyReleased(event -> {
            passwordString = passwordField.getText();
        });
    }

    public static void loginButtonClicked(){

        if (usernameString.isEmpty() || passwordString.isEmpty()){
            Constants.errorAlert("Invalid input!", "Some text fields are empty");
        } else {
            String loggingMessage = FileUtilUsers.getLoggedInUser(usernameString, User.hashPassword(passwordString));
            Optional<User> optionalUser = Optional.ofNullable(MainApplication.getActiveUser());

            if (optionalUser.isPresent()){
                logger.info(loggingMessage + ", user: " + optionalUser.get().getUsername().username());
                Constants.infoAlert("Login successful", loggingMessage);
                NavigationMethods.goToProjectSearchPage();
            } else {
                logger.error("Unsuccessful login: " + loggingMessage);
                Constants.errorAlert("Unsuccessful login!", loggingMessage);

            }
        }


    }
}
