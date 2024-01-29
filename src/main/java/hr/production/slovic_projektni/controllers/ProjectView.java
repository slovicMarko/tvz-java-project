package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.controllers.ProjectViewController;
import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.model.Project;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public interface ProjectView {


    static void showProjectView(Project project){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("project-view.fxml"));

        try {

            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();

            ProjectViewController projectViewController = fxmlLoader.getController();
            projectViewController.initialize(project);


        } catch (IOException e) {
            throw new FxmlLoadException(e);
        }

    }
}
