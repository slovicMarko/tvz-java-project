package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public interface NavigationMethods {

    static void goToEditProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-profile.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void goToManageUsers(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("manage-users.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void goToLoginPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void goToProjectSearchPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("project-search.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 1057, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void goToNewProjectPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-project.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {

            throw new RuntimeException(ex);
        }
    }
}
