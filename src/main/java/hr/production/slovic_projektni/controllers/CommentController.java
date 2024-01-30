package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.main.Main;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Optional;

public class CommentController<T> implements CustomInitializable{

    @FXML private Label commentAuthor;
    @FXML private Label numberOfLikes;
    @FXML private TextArea textArea;
    @FXML private Button likeButton;
    @FXML private Button editCommentButton;
    @FXML private Button saveCommentButton;


    Comment baseComment;
    String originalStyleClass;
    String originalInlineStyle;

    public <T> void initialize(T comment){
        baseComment = (Comment) comment;
        setData(baseComment);

        textArea.setOnKeyReleased(event -> {
            ObservableList<CharSequence> list = textArea.getParagraphs();
            textArea.setPrefRowCount(list.size() + 1);
        });

        originalStyleClass = String.join(" ", textArea.getStyleClass());
        originalInlineStyle = textArea.getStyle();
    }

    public void setData(Comment comment){
        commentAuthor.setText(comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName());
        numberOfLikes.setText(String.valueOf(comment.getLikes().size()));
        textArea.setText(comment.getContent());

        ObservableList<CharSequence> list = textArea.getParagraphs();
        textArea.setPrefRowCount(list.size() + 1);

        if (MainApplication.activeUser != null){
            likeButton.setDisable(false);
            likeButton.setText(baseComment.getLikes().contains(MainApplication.getActiveUser().getId()) ? "Dislike" : "Like");
            if (comment.getAuthor().getId().equals(MainApplication.getActiveUser().getId())){
                likeButton.setDisable(true);
                editCommentButton.setVisible(true);

            }
        }
    }


    public void editButtonClicked(){
        saveCommentButton.setVisible(true);
        textArea.setEditable(true);
        textArea.getStyleClass().clear();
        textArea.setStyle(null);
    }

    public void saveButtonClicked(){
        Comment oldComment = baseComment.clone();
        SerializableObject<Comment> commentSerializableObject = new SerializableObject.Builder<>(oldComment).build();
        baseComment.setContent(textArea.getText());

        if (!baseComment.equals(commentSerializableObject.getOriginal())){
            commentSerializableObject.setChanged(baseComment);
            SerializableMethods.serializeToFile(commentSerializableObject);
            DatabaseUtilComment.updateCommentContent(baseComment);
        }

        textArea.setEditable(false);
        saveCommentButton.setVisible(false);
        textArea.getStyleClass().setAll(originalStyleClass.split("\\s+"));
        textArea.setStyle(originalInlineStyle);

    }


    public void likeButtonClicked(){
        Comment oldComment = new Comment(baseComment.getId(), baseComment.getAuthor(), baseComment.getContent(), new ArrayList<>(baseComment.getLikes()));

        SerializableObject<Comment> commentSerializableObject = new SerializableObject.Builder<>(oldComment).build();
        if (baseComment.getLikes().contains(MainApplication.getActiveUser().getId())){
            baseComment.submitDislike();
        } else {
            baseComment.submitLike();
        }
        commentSerializableObject.setChanged(baseComment);

        if (!baseComment.equals(oldComment)){
            commentSerializableObject.setChanged(baseComment);
            SerializableMethods.serializeToFile(commentSerializableObject);
            DatabaseUtilComment.updateCommentLikes(baseComment);
        }

        setData(baseComment);
    }
}
