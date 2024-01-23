package hr.production.slovic_projektni.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.RowConstraints;

public class NewScreenController {

    @FXML private Node loginView;
    @FXML private Node registerView;
    @FXML private RowConstraints gridPaneRowWithForm;
    @FXML private RowConstraints gridPaneRowWithButtons;

    public void initialize() {
    }


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
        NavigationMethods.goToProjectSearchPage();
    }
}
