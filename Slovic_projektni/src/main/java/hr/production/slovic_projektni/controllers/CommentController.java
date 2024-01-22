package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CommentController {

    @FXML private Label commentAuthor;
    @FXML private Label commentSolution;

    public void setData(Comment comment){
        commentAuthor.setText(comment.getAuthor());
        commentSolution.setText(comment.getSolution());
    }

    public void initialize(Comment comment){
        setData(comment);
    }
}
