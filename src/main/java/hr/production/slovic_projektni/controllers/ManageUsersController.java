package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;
import hr.production.slovic_projektni.utils.FileUtilUsers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageUsersController {

    public static final Logger logger = LoggerFactory.getLogger(ManageUsersController.class);

    @FXML private GridPane usersGrid;
    public static List<User> selectedUserList = new ArrayList<>();

    public void initialize() {
        CardMethodGeneric.showCardsInContainer("user-to-manage.fxml", DatabaseUtilUsers.getUsersList(), usersGrid);
    }

    public void saveButtonClicked() {
        Map<Long, User> originalMap = DatabaseUtilUsers.getUsersMap();
        if (!selectedUserList.isEmpty()){
            for (int i = 0; i < selectedUserList.size(); i++) {
                if (!selectedUserList.get(i).equals(originalMap.get(selectedUserList.get(i).getId()))) {
                    SerializableObject<User> userSerializableObject = new SerializableObject.Builder<>(originalMap.get(selectedUserList.get(i).getId()))
                            .withChangedClass(selectedUserList.get(i))
                            .build();

                    SetSerializableDataThread<User> setSerializableDataThread = new SetSerializableDataThread<>(userSerializableObject);
                    setSerializableDataThread.run();
                }
            }
            DatabaseUtilUsers.saveManageUsersSettings(selectedUserList);
            selectedUserList.clear();
            NavigationMethods.goToProjectSearchPage();
        }else {
            Constants.warningAlert("Problem with selecting", "No user has been selected.");
        }
    }

    public void deleteButtonClicked() {
        if (!selectedUserList.isEmpty()){
            Alert alert = Constants.confirmAlert(null, "Do you want to delete selected users \n" +
                    "and also their projects and comments?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    Map<Long, User> originalMap = DatabaseUtilUsers.getUsersMap();
                    for (int i = 0; i < selectedUserList.size(); i++) {
                        if (selectedUserList.get(i).equals(originalMap.get(selectedUserList.get(i).getId()))) {
                            SerializableObject<User> userSerializableObject = new SerializableObject.Builder<>(originalMap.get(selectedUserList.get(i).getId()))
                                    .withChangedClass(new User())
                                    .build();

                            SetSerializableDataThread<User> setSerializableDataThread = new SetSerializableDataThread<>(userSerializableObject);
                            setSerializableDataThread.run();

                            FileUtilUsers.deleteUserFromFile(selectedUserList.get(i).getUsername().username());
                            DatabaseUtilUsers.deleteUser(selectedUserList.get(i).getId());
                            DatabaseUtilProject.deleteUserProjects(selectedUserList.get(i).getId());
                            DatabaseUtilComment.deleteUserComments(selectedUserList.get(i).getId());
                        }
                    }
                    selectedUserList.clear();
                    NavigationMethods.goToProjectSearchPage();
                }
            });
        } else {
            Constants.warningAlert("Problem with selecting", "No user has been selected.");
        }

    }
}
