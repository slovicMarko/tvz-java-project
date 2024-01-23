package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {

    public void showProjectSearchPage(){
        NavigationMethods.goToProjectSearchPage();
    }

    public void showNewProjectSearchPage(){
        NavigationMethods.goToNewProjectPage();
    }
}
