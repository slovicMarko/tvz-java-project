package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class UserToManageController {

    @FXML private Label usernameLabel;
    @FXML private Label fullnameLabel;
    @FXML private ChoiceBox userRoleChoiceBox;
    @FXML private CheckBox userCheckBox;


    public void initialize(User user){
        usernameLabel.setText(user.getUsername());
        fullnameLabel.setText(user.getFirstName() + " " + user.getLastName());
        userRoleChoiceBox.setValue(user.getUserRole());
        userRoleChoiceBox.setItems(FXCollections.observableArrayList(UserRole.values()));

        userRoleChoiceBox.setOnAction(event -> {
            user.setUserRole(UserRole.valueOf(userRoleChoiceBox.getValue().toString().toUpperCase()));
        });

        userCheckBox.setOnAction(event -> {
            if (userCheckBox.isSelected()) ManageUsersController.selectedUserList.add(user);
            else ManageUsersController.selectedUserList.remove(user);
        });
    }



}
