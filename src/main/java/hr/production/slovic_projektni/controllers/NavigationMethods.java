package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public interface NavigationMethods {

    static void goToProjectSearchPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectSearch.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 1057, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void goToNewProjectPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("newProject.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {

            throw new RuntimeException(ex);
        }
    }
}
