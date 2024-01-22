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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjectViewController implements Initializable{

    //private static final Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    @FXML Label titleLabel;
    @FXML Label descriptionLabel;
    @FXML Label authorLabel;
    @FXML GridPane commentsGrid;
    @FXML RowConstraints commentsRowConstraints;
    @FXML TextArea commentTextArea;

    List<Comment> commentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}


    public void initialize(Project project) {
        commentList = project.getComments();
        titleLabel.setText(project.getName());
        descriptionLabel.setText(project.getDescription());
        authorLabel.setText(project.getAuthor().getFirstName() + " " + project.getAuthor().getLastName());

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectSearch.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 1057, 600);
            MainApplication.getMainStage().setScene(scene);
            MainApplication.getMainStage().show();


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addCommentButton(){
        commentList.add(new Comment(new User(Long.parseLong("1"), "Pajo", "Patak", UserRole.ADMIN, "pajo"),
                commentTextArea.getText(), 0));
        showComments();

    }

}
