package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.main.Main;
import hr.production.slovic_projektni.model.Project;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public interface NavigationMethods {

    Logger logger = LoggerFactory.getLogger(NavigationMethods.class);

    static void loadRegularScreen(String screenName){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(screenName));
        try{
            Scene scene = new Scene(fxmlLoader.load(), screenName.equals("project-search.fxml") ? 1054 : 800, 600);

            MainApplication.setFxmlName(screenName);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    static <T, A> void loadScreenWithParams(String screenName, T param){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(screenName));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            A controllerInstance = fxmlLoader.getController();

            if (controllerInstance instanceof CustomInitializable) {
                ((CustomInitializable) controllerInstance).initialize(param);
            }
            MainApplication.setFxmlName(screenName);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException e) {
            String message = "Error while loading: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }




    static void showProjectView(Project project){
        loadScreenWithParams("project-view.fxml", project);
    }

    static void goToEditProfile(){
        loadRegularScreen("edit-profile.fxml");
    }

    static void goToManageUsers(){
        loadRegularScreen("manage-users.fxml");
    }

    static void goToLoginPage(){
        loadRegularScreen("new.fxml");
    }

    static void goToProjectSearchPage(){
        loadRegularScreen("project-search.fxml");
    }

    static void goToNewProjectPage(){
        loadRegularScreen("new-project.fxml");
    }
    static void goToChangesPage(){
        loadRegularScreen("changes.fxml");
    }

    static void goToNewProjectPage(Project project){
        loadScreenWithParams("new-project.fxml", project);
    }

}
