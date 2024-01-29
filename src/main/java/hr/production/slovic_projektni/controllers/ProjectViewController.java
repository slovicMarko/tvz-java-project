package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.model.UserRole;
import hr.production.slovic_projektni.sort.CommentSorter;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjectViewController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    @FXML Label titleLabel;
    @FXML Label descriptionLabel;
    @FXML Label authorLabel;
    @FXML Label fileNameLabel;
    @FXML GridPane commentsGrid;
    @FXML RowConstraints commentsRowConstraints;
    @FXML TextArea commentTextArea;
    @FXML Button addComment;
    @FXML Button addFile;
    @FXML Button editButton;
    @FXML Button deleteButton;

    List<Comment> commentList;
    private static Long projectId;
    private static Project projectCopy;

    public void initialize(Project project) {
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        projectId = project.getId();
        projectCopy = project;
        commentList = project.getComments();
        titleLabel.setText(project.getName());
        descriptionLabel.setText(project.getDescription());
        authorLabel.setText(project.getAuthor().getFirstName() + " " + project.getAuthor().getLastName());

        User activeUser = MainApplication.getActiveUser();

        if (activeUser != null){
            addComment.setDisable(false);
            addFile.setDisable(false);
            commentTextArea.setDisable(false);
            if (activeUser.equals(project.getAuthor())){
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            }
        }

        showComments();
    }

    private void showComments() {
        commentList = commentList.stream().sorted(new CommentSorter()).collect(Collectors.toList());

        int column = 0;
        int row = 1;

        try {
            for (Comment comment : commentList) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("comment.fxml"));

                HBox commentCard = fxmlLoader.load();
                CommentController commentController = fxmlLoader.getController();
                commentController.initialize(comment);

                commentsGrid.add(commentCard, column, row++);
                GridPane.setMargin(commentCard, new Insets(10));
            }
        } catch (IOException e) {
            String message = "Error while adding list of comments";
            logger.error(message, e);
            throw new FxmlLoadException(e);
        }
    }

    public void backButton() {
        NavigationMethods.goToProjectSearchPage();
    }

    public void editProjectButton(){
        NavigationMethods.goToNewProjectPage(projectCopy);
    }

    public void deleteProjectButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                DatabaseUtilProject.deleteProject(projectId);
                NavigationMethods.goToProjectSearchPage();
            }
        });
    }



    public void addCommentButton(){
        Long commentId = DatabaseUtilComment.saveComment(MainApplication.getActiveUser().getId(),
                commentTextArea.getText(),
                projectId);

        if (commentId != null){
            commentList.add(new Comment(commentId ,MainApplication.getActiveUser(),
                    commentTextArea.getText(), new ArrayList<>()));
            showComments();
        }
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
