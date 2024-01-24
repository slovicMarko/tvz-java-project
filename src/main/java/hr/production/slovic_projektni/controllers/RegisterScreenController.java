package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static hr.production.slovic_projektni.model.User.hashPassword;

public class RegisterScreenController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static String usernameString;
    private static String passwordString;
    private static String firstNameString;
    private static String lastNameString;


    public void initialize(){
        firstNameField.setOnKeyReleased(event -> {
            firstNameString = firstNameField.getText();
        });

        lastNameField.setOnKeyReleased(event -> {
            lastNameString = lastNameField.getText();
        });

        usernameField.setOnKeyReleased(event -> {
            usernameString = usernameField.getText();
        });

        passwordField.setOnKeyReleased(event -> {
            passwordString = passwordField.getText();
        });
    }

    public static void registerButtonClicked(){
        Long nextId = DatabaseUtilUsers.nextUserId();
        try{
            User newUser = new User(nextId, firstNameString, lastNameString, usernameString, User.hashPassword(passwordString), UserRole.STUDENT);
            FileUtilUsers.saveUserToFile(newUser);
            DatabaseUtilUsers.saveUser(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NavigationMethods.goToProjectSearchPage();
    }









}
