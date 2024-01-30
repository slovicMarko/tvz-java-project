package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditProfileController {

    @FXML private Label titleLabel;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField userRoleField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField repeatedPasswordField;


    public void initialize(){
        User activeUser = MainApplication.getActiveUser();
        titleLabel.setText("Edit user: " + activeUser.getUsername().username());
        firstNameField.setText(activeUser.getFirstName());
        lastNameField.setText(activeUser.getLastName());
        userRoleField.setText(activeUser.getUserRole().getName());
    }

    public void saveChangedButton(){
        User activeUser = MainApplication.getActiveUser();
        if (User.hashPassword(oldPasswordField.getText()).equals(activeUser.getPasswordHash())){
            if (newPasswordField.getText().equals(repeatedPasswordField.getText())){
                SerializableObject<User> userSerializableObject = new SerializableObject.Builder<>(activeUser)
                        .withChangedClass(new User(activeUser.getId(), activeUser.getFirstName(), activeUser.getLastName(), activeUser.getUsername(),
                                User.hashPassword(newPasswordField.getText()),activeUser.getUserRole())).build();

                SerializableMethods.serializeToFile(userSerializableObject);
                FileUtilUsers.changeUserPassword(newPasswordField.getText());
                DatabaseUtilUsers.changeUserPassword(activeUser.getId(), newPasswordField.getText());
                NavigationMethods.goToProjectSearchPage();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Password not changed!");
                alert.setContentText("New password not match repeated.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password not changed!");
            alert.setContentText("Old password isn't correct.");
            alert.showAndWait();
        }
    }

}
