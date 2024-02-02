package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CommentController implements CustomInitializable{

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @FXML private Label commentAuthor;
    @FXML private Label numberOfLikes;
    @FXML private TextArea textArea;
    @FXML private Button likeButton;
    @FXML private ImageView deleteImageView;
    @FXML private ImageView saveImageView;
    @FXML private ImageView editImageView;

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

        initializeIconsForEditComment();
    }

    private void initializeIconsForEditComment() {
        InputStream binStream, editStream, saveStream;

        try {
            binStream = new FileInputStream(Constants.BIN_PATH);
            editStream = new FileInputStream(Constants.EDIT_PATH);
            saveStream = new FileInputStream(Constants.SAVE_PATH);
            deleteImageView.setImage(new Image(binStream));
            editImageView.setImage(new Image(editStream));
            saveImageView.setImage(new Image(saveStream));
        } catch (FileNotFoundException e) {
            String message = "Problem with loading comment icons";
            logger.error(message);
            Constants.errorAlert(null, message);
        }

    }

    public void setData(Comment comment){
        commentAuthor.setText(comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName());
        numberOfLikes.setText(String.valueOf(comment.getLikes().size()));
        textArea.setText(comment.getContent());

        ObservableList<CharSequence> list = textArea.getParagraphs();
        textArea.setPrefRowCount(list.size() + 1);

        if (MainApplication.getActiveUser() != null){
            likeButton.setDisable(false);
            likeButton.setText(baseComment.getLikes().contains(MainApplication.getActiveUser().getId()) ? "Dislike" : "Like");
            if (comment.getAuthor().getId().equals(MainApplication.getActiveUser().getId())){
                deleteImageView.setVisible(true);
                editImageView.setVisible(true);
                likeButton.setDisable(true);
            }
        }
    }

    public void likeButtonClicked(){
        Comment oldComment = new Comment(baseComment.getId(), baseComment.getAuthor(), baseComment.getContent(), baseComment.getPostDate(), new ArrayList<>(baseComment.getLikes()));

        SerializableObject<Comment> commentSerializableObject = new SerializableObject.Builder<>(oldComment).build();
        if (baseComment.getLikes().contains(MainApplication.getActiveUser().getId())){
            baseComment.submitDislike();
        } else {
            baseComment.submitLike();
        }

        commentSerializableObject.setChanged(baseComment);

        if (!baseComment.equals(oldComment)){
            commentSerializableObject.setChanged(baseComment);

            SetSerializableDataThread<Comment> setSerializableDataThread = new SetSerializableDataThread<>(commentSerializableObject);
            setSerializableDataThread.run();

            DatabaseUtilComment.updateCommentLikes(baseComment);
        }

        setData(baseComment);
    }

    public void editButtonClicked(){
        saveImageView.setVisible(true);
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

            SetSerializableDataThread<Comment> setSerializableDataThread = new SetSerializableDataThread<>(commentSerializableObject);
            setSerializableDataThread.run();

            DatabaseUtilComment.updateCommentContent(baseComment);
        }

        textArea.setEditable(false);
        saveImageView.setVisible(false);
        textArea.getStyleClass().setAll(originalStyleClass.split("\\s+"));
        textArea.setStyle(originalInlineStyle);

    }

    public void deleteCommentButton(){
        Alert alert = Constants.confirmAlert("Delete comment", "Dou you want to delete your comment?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){

                SerializableObject<Comment> commentSerializableObject = new SerializableObject.Builder<>(baseComment)
                        .withChangedClass(new Comment()).build();

                SetSerializableDataThread<Comment> setSerializableDataThread = new SetSerializableDataThread<>(commentSerializableObject);
                setSerializableDataThread.run();

                DatabaseUtilComment.deleteComment(baseComment.getId());
                NavigationMethods.goToProjectSearchPage();
            }
        });
    }
}
