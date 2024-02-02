package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NewScreenController {

    Logger logger = LoggerFactory.getLogger(NewScreenController.class);


    @FXML private RowConstraints gridPaneRowWithButtons;
    @FXML private GridPane gridPaneContainer;

    private static final String LOGIN_VIEW = "login-view.fxml";
    private static final String REGISTER_VIEW = "register-view.fxml";
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
            String message = "Error while loading: " + name;
            logger.error(message);
            throw new FxmlLoadException(e);
        }
        return false;
    }

    public void showProjectSearchPage() {
        NavigationMethods.goToProjectSearchPage();
    }
}
