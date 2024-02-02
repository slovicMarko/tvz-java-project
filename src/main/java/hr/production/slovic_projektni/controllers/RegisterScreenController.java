package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.exception.ExistingUserException;
import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import hr.production.slovic_projektni.model.Username;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterScreenController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterScreenController.class);

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static String usernameString = "";
    private static String passwordString = "";
    private static String firstNameString = "";
    private static String lastNameString = "";


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

        if (firstNameString.isEmpty() || lastNameString.isEmpty()
                || usernameString.isEmpty() || passwordString.isEmpty()){
            logger.error("Empty text fields while user register!");
            Constants.errorAlert("Invalid input!", "Some text fields are empty");
        } else {
            try{
                User newUser = new User(Long.parseLong("1"), firstNameString, lastNameString, new Username(usernameString), User.hashPassword(passwordString), UserRole.STUDENT);

                FileUtilUsers.createUserInFile(usernameString, User.hashPassword(passwordString));
                DatabaseUtilUsers.saveUser(newUser);
                Constants.infoAlert("Registration", "Successful registration\n" +
                        "Welcome: " + firstNameString + " " + lastNameString);
                MainApplication.setActiveUser(DatabaseUtilUsers.activeUser(newUser));

                SerializableObject<User> userSerializableObject = new SerializableObject.Builder<>(new User())
                        .withChangedClass(newUser).build();

                SetSerializableDataThread<User> setSerializableDataThread = new SetSerializableDataThread<>(userSerializableObject);
                setSerializableDataThread.run();

                NavigationMethods.goToProjectSearchPage();
            } catch (ExistingUserException e) {
                String message = "Already exist user with same username!";
                logger.error(message + ", username: " + usernameString);
                Constants.warningAlert("Unsuccessful registration", message);

            }
        }
    }









}
