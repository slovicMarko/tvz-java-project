package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersController {

    @FXML private GridPane usersGrid;
    private List<User> userList;
    public static List<User> selectedUserList = new ArrayList<>();


    public void initialize(){
        showUsers();
    }

    public void saveButtonClicked(){
        for (User user : selectedUserList){
            System.out.println(user.getFirstName() + " " + user.getLastName() + ", " + user.getUserRole().getName());
        }
    }


    private void showUsers(){
        userList = DatabaseUtilUsers.getUsersList();

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < userList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-to-manage.fxml"));

                HBox card = fxmlLoader.load();
                UserToManageController userToManageController = fxmlLoader.getController();
                userToManageController.initialize(userList.get(i));

                usersGrid.add(card, column, row++);
                GridPane.setMargin(card, new Insets(10));
            }
        } catch (IOException e) {
            String errorMessage = "Error while adding comments";
            //logger.error(errorMessage, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
