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


    public void initialize() {
        CardMethodGeneric.showCardsOnGridPane("user-to-manage.fxml", DatabaseUtilUsers.getUsersList(), usersGrid);
    }

    public void saveButtonClicked(){
        DatabaseUtilUsers.saveManageUsersSettings(selectedUserList);
        selectedUserList.clear();
        NavigationMethods.goToProjectSearchPage();
    }

}
