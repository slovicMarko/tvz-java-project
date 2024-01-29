package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.main.Main;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
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
        commentAuthor.setText(comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName());
        commentSolution.setText(comment.getContent());
        numberOfLikes.setText(String.valueOf(comment.getLikes().size()));

        if (MainApplication.activeUser != null){
            likeButton.setDisable(false);
            likeButton.setText(baseComment.getLikes().contains(MainApplication.getActiveUser().getId()) ? "Dislike" : "Like");
        }
    }

    public void likeButtonClicked(){
        if (baseComment.getLikes().contains(MainApplication.getActiveUser().getId())){
            baseComment.submitDislike();
        } else {
            baseComment.submitLike();
        }
        DatabaseUtilComment.updateCommentLikes(baseComment);
        setData(baseComment);
    }
}
