package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.exception.FxmlLoadException;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.sort.CommentSorter;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectViewController implements CustomInitializable {

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

    public <T> void initialize(T project) {
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        if (project instanceof Project){
            projectCopy = (Project) project;
            projectId = projectCopy.getId();
            commentList = projectCopy.getComments();
            titleLabel.setText(projectCopy.getName());
            descriptionLabel.setText(projectCopy.getDescription());
            authorLabel.setText(projectCopy.getAuthor().getFirstName() + " " + projectCopy.getAuthor().getLastName());
        }

        User activeUser = MainApplication.getActiveUser();

        if (activeUser != null){
            addComment.setDisable(false);
            addFile.setDisable(false);
            commentTextArea.setDisable(false);
            if (activeUser.equals(projectCopy.getAuthor())){
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            }
        }

        showComments();
    }

    private void showComments() {
        commentList = commentList.stream().sorted(new CommentSorter()).collect(Collectors.toList());

        CardMethodGeneric.showCardsOnGridPane("comment.fxml", commentList, commentsGrid);
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
