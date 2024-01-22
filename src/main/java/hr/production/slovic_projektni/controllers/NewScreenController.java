package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class NewScreenController {

    @FXML private Node loginView;
    @FXML private Node registerView;
    @FXML private RowConstraints gridPaneRowWithForm;
    @FXML private RowConstraints gridPaneRowWithButtons;

    public void initialize() {    }


    public void showLoginView(){
        if (loginView.isVisible()){
            showProjectSearchPage();
        } else {
            registerView.setVisible(false);
            gridPaneRowWithForm.setPrefHeight(184);
            gridPaneRowWithButtons.setPrefHeight(220);
            loginView.setVisible(true);
        }


    }

    public void showRegisterView(){
        if (registerView.isVisible()){
            showProjectSearchPage();
        } else {
            registerView.setVisible(true);
            gridPaneRowWithForm.setPrefHeight(212);
            gridPaneRowWithButtons.setPrefHeight(192);
            loginView.setVisible(false);
        }
    }

    public void showProjectSearchPage(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectSearch.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 1057, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
