package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.model.Project;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public interface NavigationMethods {

    Logger logger = LoggerFactory.getLogger(NavigationMethods.class);

    static void goToEditProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-profile.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    static void goToManageUsers(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("manage-users.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    static void goToLoginPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    static void goToProjectSearchPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("project-search.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 1057, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    static void goToNewProjectPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-project.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    static void goToNewProjectPage(Project project){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-project.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            NewProjectController newProjectController = fxmlLoader.getController();
            newProjectController.initialize(project);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }
}
