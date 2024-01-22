package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable{

    //private static final Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    @FXML Label titleLabel;
    @FXML Label descriptionLabel;
    @FXML Label authorLabel;
    @FXML GridPane commentsGrid;
    @FXML RowConstraints commentsRowConstraints;

    private List<Comment> comments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    public void initialize(Project project) {
        titleLabel.setText(project.getProjectName());
        descriptionLabel.setText(project.getProjectDescription());
        authorLabel.setText(project.getProjectOwner().getFname() + " " + project.getProjectOwner().getLname());

        List<Comment> commentList = new ArrayList<>(commentsData());

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


    private List<Comment> commentsData() {
        List<Comment> commentsList = new ArrayList<>();

        Comment comment1 = new Comment();
        comment1.setAuthor("John Doe");
        comment1.setSolution("""
                Moje rjesenje za Fibbonacijev niz
                public void initialize(Project project){
                                
                        titleLabel.setText(project.getProjectName());
                        descriptionLabel.setText(project.getProjectDescription());
                        authorLabel.setText(project.getProjectOwner().getFname() + " " + project.getProjectOwner().getLname());
                    }           
                """);
        commentsList.add(comment1);

        Comment comment2 = new Comment();

        comment2.setAuthor("Josip Josipović");
        comment2.setSolution("""
                Mene je to također mučilo pa pogledaj ovaj video:        
                https://www.youtube.com/watch?v=NHPLjIkrNvI&ab_channel=MahmoudHamwi
                """);
        commentsList.add(comment2);

        Comment comment3= new Comment();

        comment3.setAuthor("Marko Slovic");
        comment3.setSolution("""
                Moja NewScreenController klasa
                
                public class NewScreenController {
                                
                    @FXML private Node loginView;
                    @FXML private Node registerView;
                    @FXML private RowConstraints gridPaneRowWithForm;
                    @FXML private RowConstraints gridPaneRowWithButtons;
                                
                    public void initialize() {    }
                                
                                
                    public void showLoginView(){
                        if (loginView.isVisible()){
                            showProjectSearchPage();
                        } else {
                            registerView.setVisible(false);
                            gridPaneRowWithForm.setPrefHeight(156);
                            gridPaneRowWithButtons.setPrefHeight(248);
                            loginView.setVisible(true);
                        }
                                
                                
                    }
                                
                    public void showRegisterView(){
                        if (registerView.isVisible()){
                            showProjectSearchPage();
                        } else {
                            registerView.setVisible(true);
                            gridPaneRowWithForm.setPrefHeight(212);
                            gridPaneRowWithButtons.setPrefHeight(192);
                            loginView.setVisible(false);
                        }
                    }
                                
                    public void showProjectSearchPage(){
                        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("projectSearch.fxml"));
                        try{
                            Scene scene = new Scene(fxmlLoader.load(), 1057, 600);
                            MainApplication.getMainStage().setScene(scene);
                            MainApplication.getMainStage().show();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                """);
        commentsList.add(comment3);

        Comment comment4 = new Comment();
        comment4.setAuthor("Zekoslav Mrkva");
        comment4.setSolution("""
                Samo radi sve ce se to vratit s kamatama,
                svidja mi se rjesenje od Josipa.
                """);

        commentsList.add(comment4);

        return commentsList;
    }


}
