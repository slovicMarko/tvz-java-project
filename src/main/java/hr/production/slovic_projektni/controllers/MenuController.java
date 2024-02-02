package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MenuController {

    public static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @FXML MenuItem manageUsersAdmin;
    @FXML MenuItem editProfile;
    @FXML MenuItem newProjectMenu;
    @FXML Menu changesMenu;

    private Optional<User> optionalUser;

    public void initialize(){

        optionalUser = Optional.ofNullable(MainApplication.getActiveUser());

        if (optionalUser.isPresent()){
            if (optionalUser.get().getUserRole().equals(UserRole.ADMIN)){
                manageUsersAdmin.setDisable(false);
                changesMenu.setVisible(true);
            }
            editProfile.setDisable(false);
            newProjectMenu.setDisable(false);
        }
    }


    public void showEditProfileScreen(){
        NavigationMethods.goToEditProfile();
    }

    public void showManageUsers(){
        NavigationMethods.goToManageUsers();
    }

    public void showLoginScreen(){
        MainApplication.logoutUser();
        NavigationMethods.goToLoginPage();
    }
    public void showProjectSearchPage(){
        NavigationMethods.goToProjectSearchPage();
    }
    public void showNewProjectSearchPage(){
        NavigationMethods.goToNewProjectPage();
    }
    public void showChangesPage(){
        NavigationMethods.goToChangesPage();
    }
}
