package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.Optional;

public class MenuController {

    @FXML MenuItem manageUsersAdmin;
    @FXML MenuItem editProfile;
    @FXML MenuItem newProjectMenu;
    @FXML Menu changesMenu;


    public void initialize(){
        try{
            if (MainApplication.getActiveUser().getUserRole().equals(UserRole.ADMIN)){
                manageUsersAdmin.setDisable(false);
                changesMenu.setVisible(true);
            }
            if (Optional.of(MainApplication.getActiveUser().getUserRole()).isPresent()){
                editProfile.setDisable(false);
                newProjectMenu.setDisable(false);
            }
        } catch (NullPointerException ex){}
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
