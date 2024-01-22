package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {

    public void showStudentLoginScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginView.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setTitle("ProblemHelper!");
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showRegisterScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("registerView.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setTitle("Register!");
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void showProjectSearchPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectSearch.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            MainApplication.getMainStage().setTitle("ProjectHelper!");
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {

            throw new RuntimeException(ex);
        }
    }
}
