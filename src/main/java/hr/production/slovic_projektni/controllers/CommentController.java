package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CommentController {

    @FXML private Label commentAuthor;
    @FXML private Label commentSolution;
    @FXML private Label numberOfLikes;

    Comment baseComment;

    public void initialize(Comment comment){
        baseComment = comment;
        setData(comment);
    }

    public void setData(Comment comment){
        commentAuthor.setText(comment.author().getFirstName() + " " + comment.author().getLastName());
        commentSolution.setText(comment.content());
        numberOfLikes.setText(comment.getLikes().toString());
    }

    public void likeButtonClicked(){
        setData(baseComment.submitLike());
    }
}
