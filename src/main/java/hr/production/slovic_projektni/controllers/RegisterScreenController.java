package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.ExistingUserException;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import hr.production.slovic_projektni.model.Username;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

        try{
            User newUser = new User(Long.parseLong("1"), firstNameString, lastNameString, new Username(usernameString), User.hashPassword(passwordString), UserRole.STUDENT);

            FileUtilUsers.createUserInFile(usernameString, User.hashPassword(passwordString));
            DatabaseUtilUsers.saveUser(newUser);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Registracija");
            alert.setContentText("Uspješno ste se registrirali u aplikaciju.");
            alert.showAndWait();
            MainApplication.setActiveUser(DatabaseUtilUsers.activeUser(newUser));

            SerializableObject<User> userSerializableObject = new SerializableObject.Builder<>(new User())
                    .withChangedClass(newUser).build();

            SetSerializableDataThread<User> setSerializableDataThread = new SetSerializableDataThread<>(userSerializableObject);
            setSerializableDataThread.run();

            NavigationMethods.goToProjectSearchPage();
        } catch (ExistingUserException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Neuspješna registracija!");
            alert.setContentText("Postoji korisnik s istim korisničkim imenom.");
            alert.showAndWait();
        }
    }









}
