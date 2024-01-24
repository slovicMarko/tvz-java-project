package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.utils.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class NewScreenController {

    @FXML private RowConstraints gridPaneRowWithButtons;
    @FXML private GridPane gridPaneContainer;

    private static final String LOGIN_VIEW = "loginView.fxml";
    private static final String REGISTER_VIEW = "registerView.fxml";
    private Boolean loginView = true;
    private Boolean registerView = true;

    public void initialize() {}

    public void showLoginView() {
        gridPaneRowWithButtons.setMinHeight(0);
        if (loginView) {
            if (!registerView){
                registerView = true;
                gridPaneContainer.getChildren().clear();
            }
            loginView = showLoginRegisterCard(LOGIN_VIEW);

        } else {
            LoginScreenController.loginButtonClicked();
        }
    }

    public void showRegisterView() {
        gridPaneRowWithButtons.setMinHeight(0);
        if (registerView) {
            if (!loginView){
                loginView = true;
                gridPaneContainer.getChildren().clear();
            }
            registerView = showLoginRegisterCard(REGISTER_VIEW);

        } else {
            RegisterScreenController.registerButtonClicked();
        }
    }

    private Boolean showLoginRegisterCard(String name) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name));
        try {
            VBox card = fxmlLoader.load();
            gridPaneContainer.add(card, 0, 1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void showProjectSearchPage() {
        NavigationMethods.goToProjectSearchPage();
    }
}
