package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public interface CardMethodGeneric {

    Logger logger = LoggerFactory.getLogger(CardMethodGeneric.class);

    static <T, A, E> void showCardsInContainer(String cardName, List<T> listOfElements, E container) {
        int column = 0;
        int row = 1;

        try {
            for (T element : listOfElements) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(cardName));

                HBox commentCard = fxmlLoader.load();
                A controllerInstance = fxmlLoader.getController();
                if (controllerInstance instanceof CustomInitializable) {
                    ((CustomInitializable) controllerInstance).initialize(element);
                }
                if (container instanceof GridPane){
                    ((GridPane) container).add(commentCard, column, row++);
                    GridPane.setMargin(commentCard, new Insets(10));
                }

            }
        } catch (IOException e) {
            String message = "Error while adding cards: " + e.getMessage();
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }
}
