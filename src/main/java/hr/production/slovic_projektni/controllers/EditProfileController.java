package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import javafx.fxml.FXML;
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
        titleLabel.setText("Edit user: " + activeUser.getUsername());
        firstNameField.setText(activeUser.getFirstName());
        lastNameField.setText(activeUser.getLastName());
        userRoleField.setText(activeUser.getUserRole().getName());

    }

    public void saveChangedButton(){
        User activeUser = MainApplication.getActiveUser();
        if (User.hashPassword(oldPasswordField.getText()).equals(activeUser.getPasswordHash())){
            if (newPasswordField.getText().equals(repeatedPasswordField.getText())){
                System.out.println("sifra uspjesno promijenjena");
            }else {
                System.out.println("sifre se ne podudaraju");
            }
        }else {
            System.out.println("netocna stara sifra");
        }
    }

}
