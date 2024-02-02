package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.DateAndTime;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.sort.CommentSorter;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectViewController implements CustomInitializable {

    private static final Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    @FXML Label titleLabel;
    @FXML Label descriptionLabel;
    @FXML Label authorLabel;
    @FXML GridPane commentsGrid;
    @FXML RowConstraints commentsRowConstraints;
    @FXML TextArea commentTextArea;
    @FXML Button addComment;
    @FXML Button editButton;
    @FXML Button deleteButton;

    private List<Comment> commentList;
    private static Long projectId;
    private static Project projectCopy;


    public <T> void initialize(T project) {
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        if (project instanceof Project){
            projectCopy = (Project) project;
            projectId = projectCopy.getId();

            commentList = projectCopy.getComments();
            showComments();

            titleLabel.setText(projectCopy.getName());
            descriptionLabel.setText(projectCopy.getDescription());
            authorLabel.setText(projectCopy.getAuthor().getFirstName() + " " + projectCopy.getAuthor().getLastName());
        }

        User activeUser = MainApplication.getActiveUser();

        if (activeUser != null){
            addComment.setDisable(false);
            commentTextArea.setDisable(false);
            if (activeUser.equals(projectCopy.getAuthor())){
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            }
        }

    }


    private void showComments() {
        commentList = commentList.stream().sorted(new CommentSorter()).collect(Collectors.toList());

        CardMethodGeneric.showCardsInContainer("comment.fxml", commentList, commentsGrid);
    }

    public void backButton() {
        NavigationMethods.goToProjectSearchPage();
    }

    public void editProjectButton(){
        NavigationMethods.goToNewProjectPage(projectCopy);
    }

    public void deleteProjectButton(){
        Alert alert = Constants.confirmAlert(null, "Are you sure you want to delete?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                DatabaseUtilComment.deleteAllProjectComments(projectId);
                DatabaseUtilProject.deleteProject(projectId);

                SerializableObject<Project> projectSerializableObject = new SerializableObject.Builder<>(projectCopy)
                        .withChangedClass(new Project()).build();

                SetSerializableDataThread<Project> setSerializableDataThread = new SetSerializableDataThread<>(projectSerializableObject);
                setSerializableDataThread.run();

                NavigationMethods.goToProjectSearchPage();
            }
        });
    }

    public void addCommentButton(){
        Long commentId = DatabaseUtilComment.saveComment(MainApplication.getActiveUser().getId(),
                commentTextArea.getText(),
                projectId);

        if (commentId != null){
            Comment newComment = new Comment(commentId , MainApplication.getActiveUser(),
                    commentTextArea.getText(), new DateAndTime(LocalDateTime.now()),new ArrayList<>());

            SerializableObject<Comment> commentSerializableObject = new SerializableObject.Builder<>(new Comment())
                    .withChangedClass(newComment).build();

            SetSerializableDataThread<Comment> setSerializableDataThread = new SetSerializableDataThread<>(commentSerializableObject);
            setSerializableDataThread.run();

            commentList.add(newComment);
            showComments();
        }
    }
}
