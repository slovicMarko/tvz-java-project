package hr.production.slovic_projektni;

import hr.production.slovic_projektni.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static Stage mainStage;

    public static User activeUser;

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User user) {
        MainApplication.activeUser = user;
    }

    public static void logoutUser() {
        MainApplication.activeUser = null;
    }


    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("ProjectHelper!");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }

}