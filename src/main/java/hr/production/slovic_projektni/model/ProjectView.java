package hr.production.slovic_projektni.model;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.controllers.ProjectViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ProjectView {

    private static ProjectViewController projectViewController;


    public ProjectView(Project project) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectView.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.projectViewController = fxmlLoader.getController();
        projectViewController.initialize(project);
    }

    public static void showProjectView(Project project){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectView.fxml"));

        try {

            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 800, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();

            projectViewController = fxmlLoader.getController();
            projectViewController.initialize(project);


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
