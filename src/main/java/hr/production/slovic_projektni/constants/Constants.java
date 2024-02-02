package hr.production.slovic_projektni.constants;

import javafx.scene.control.Alert;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");

    public static final String BIN_PATH = "D:\\OneDrive - Zagreb University of Applied Science\\3. semestar\\JAVA\\SEMINARSKI\\tvz-java-project\\images\\bin.png";
    public static final String SAVE_PATH = "D:\\OneDrive - Zagreb University of Applied Science\\3. semestar\\JAVA\\SEMINARSKI\\tvz-java-project\\images\\check.png";
    public static final String EDIT_PATH = "D:\\OneDrive - Zagreb University of Applied Science\\3. semestar\\JAVA\\SEMINARSKI\\tvz-java-project\\images\\edit.png";




    public static void errorAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void warningAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void infoAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Alert confirmAlert(String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }

}
