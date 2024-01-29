package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersController {


    public static final Logger logger = LoggerFactory.getLogger(ManageUsersController.class);


    @FXML private GridPane usersGrid;
    public static List<User> selectedUserList = new ArrayList<>();


    public void initialize(){
        showUsers();
    }

    public void saveButtonClicked(){
        DatabaseUtilUsers.saveManageUsersSettings(selectedUserList);
        selectedUserList.clear();
        NavigationMethods.goToProjectSearchPage();
    }


    private void showUsers(){
        List<User> userList = DatabaseUtilUsers.getUsersList();

        int column = 0;
        int row = 1;

        try {
            for (User user : userList) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-to-manage.fxml"));

                HBox card = fxmlLoader.load();
                UserToManageController userToManageController = fxmlLoader.getController();
                userToManageController.initialize(user);

                usersGrid.add(card, column, row++);
                GridPane.setMargin(card, new Insets(10));
            }
        } catch (IOException e) {
            String message = "Error while listing users in \"Manage users\"";
            logger.error(message, e);
            throw new FxmlLoadException(message, e);
        }
    }

}
