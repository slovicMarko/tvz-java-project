package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import hr.production.slovic_projektni.sort.CommentSorter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjectViewController implements Initializable{

    //private static final Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    @FXML Label titleLabel;
    @FXML Label descriptionLabel;
    @FXML Label authorLabel;
    @FXML Label fileNameLabel;
    @FXML GridPane commentsGrid;
    @FXML RowConstraints commentsRowConstraints;
    @FXML TextArea commentTextArea;
    @FXML Button addComment;
    @FXML Button addFile;

    List<Comment> commentList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}


    public void initialize(Project project) {
        commentList = project.getComments();
        titleLabel.setText(project.getName());
        descriptionLabel.setText(project.getDescription());
        authorLabel.setText(project.getAuthor().getFirstName() + " " + project.getAuthor().getLastName());

        User activeUser = MainApplication.getActiveUser();
        try{
            if (Optional.of(activeUser).isPresent()){
                addComment.setDisable(false);
                addFile.setDisable(false);
                commentTextArea.setDisable(false);
            }
        } catch (NullPointerException ex){
            System.out.println("Niste prijavljeni.");
        }


        showComments();
    }

    private void showComments() {
        commentList = commentList.stream().sorted(new CommentSorter()).collect(Collectors.toList());

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < commentList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("comment.fxml"));

                HBox commentCard = fxmlLoader.load();
                CommentController commentController = fxmlLoader.getController();
                commentController.initialize(commentList.get(i));

                commentsGrid.add(commentCard, column, row++);
                GridPane.setMargin(commentCard, new Insets(10));
            }
        } catch (IOException e) {
            String errorMessage = "Error while adding comments";
            //logger.error(errorMessage, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void backButton() {
        NavigationMethods.goToProjectSearchPage();
    }

    public void addCommentButton(){
        addComment.setDisable(false);
        commentList.add(new Comment(MainApplication.getActiveUser(),
                commentTextArea.getText(), 0));
        showComments();
    }

    public void addFileButton() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        configureFileChooser(fileChooser);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("window.fxml"));
        Parent newSceneRoot = null;
        try {
            newSceneRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene newScene = new Scene(newSceneRoot, 300, 200);
        var selectedFile = fileChooser.showOpenDialog(newScene.getWindow());
        if (selectedFile != null) {
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            fileNameLabel.setText(selectedFile.getAbsolutePath());
//            InputStream stream = new FileInputStream(selectedFile.getPath());
//            Image image = new Image(stream);
//            imageView.setImage(image);
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

}
