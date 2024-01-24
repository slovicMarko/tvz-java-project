package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;


public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static String usernameString;
    private static String passwordString;


    public void initialize(){
        usernameField.setOnKeyReleased(event -> {
            usernameString = usernameField.getText();
        });

        passwordField.setOnKeyReleased(event -> {
            passwordString = passwordField.getText();
        });
    }

    public static void loginButtonClicked(){
        try{
            User activeUser = FileUtilUsers.getLoggedInUser(usernameString, User.hashPassword(passwordString));

            if (Optional.of(activeUser).isPresent()){
                System.out.println("Uspjesna prijava");
                MainApplication.setActiveUser(DatabaseUtilUsers.activeUser(activeUser));
                NavigationMethods.goToProjectSearchPage();

            } else {
                System.out.println("Neuspješna prijava");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
