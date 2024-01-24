package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Optional;

public class CommentController {

    @FXML private Label commentAuthor;
    @FXML private Label commentSolution;
    @FXML private Label numberOfLikes;
    @FXML private Button likeButton;

    Comment baseComment;

    public void initialize(Comment comment){
        baseComment = comment;
        setData(comment);

    }

    public void setData(Comment comment){
        commentAuthor.setText(comment.author().getFirstName() + " " + comment.author().getLastName());
        commentSolution.setText(comment.content());
        numberOfLikes.setText(comment.getLikes().toString());
        try{
            if (Optional.of(MainApplication.activeUser).isPresent()){
                likeButton.setDisable(false);
            }
        } catch (NullPointerException ex){
            System.out.println("Niste prijavljeni.");
        }

    }

    public void likeButtonClicked(){
        setData(baseComment.submitLike());
    }
}
