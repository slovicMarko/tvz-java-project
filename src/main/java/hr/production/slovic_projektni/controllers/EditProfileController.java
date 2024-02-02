package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditProfileController {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileController.class);

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
        if (oldPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() || repeatedPasswordField.getText().isEmpty()){
            logger.error("Empty text fields while changing password!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid input!");
            alert.setContentText("Some text fields are empty");
            alert.showAndWait();
        } else {
            if (User.hashPassword(oldPasswordField.getText()).equals(activeUser.getPasswordHash())){
                if (newPasswordField.getText().equals(repeatedPasswordField.getText())){
                    SerializableObject<User> userSerializableObject = new SerializableObject.Builder<>(activeUser)
                            .withChangedClass(new User(activeUser.getId(), activeUser.getFirstName(), activeUser.getLastName(), activeUser.getUsername(),
                                    User.hashPassword(newPasswordField.getText()),activeUser.getUserRole())).build();

                    SetSerializableDataThread<User> setSerializableDataThread = new SetSerializableDataThread<>(userSerializableObject);
                    setSerializableDataThread.run();

                    FileUtilUsers.changeUserPassword(newPasswordField.getText());
                    DatabaseUtilUsers.changeUserPassword(activeUser.getId(), newPasswordField.getText());
                    logger.info("Password changed successfully. User: " + MainApplication.getActiveUser().getUsername());
                    NavigationMethods.goToProjectSearchPage();
                } else {
                    logger.error("New password not match repeated. User: " + MainApplication.getActiveUser().getUsername());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Password not changed!");
                    alert.setContentText("New password not match repeated.");
                    alert.showAndWait();
                }
            } else {
                logger.error("Old password isn't correct. User: " + MainApplication.getActiveUser().getUsername());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Password not changed!");
                alert.setContentText("Old password isn't correct.");
                alert.showAndWait();
            }
        }

    }

}
