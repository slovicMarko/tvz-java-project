package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CommentController {

    @FXML private Label commentAuthor;
    @FXML private Label commentSolution;
    @FXML private Label numberOfLikesLabel;

    public void setData(Comment comment){
        commentAuthor.setText(comment.author().getFirstName() + " " + comment.author().getLastName());
        commentSolution.setText(comment.content());
        numberOfLikesLabel.setText(comment.getLikes().toString());
    }

    public void initialize(Comment comment){
        setData(comment);
    }
}
