package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    public static List<Project> getProjects(){

        List<User> users = DatabaseUtilUsers.getUsersList();


        List<Project> projects = new ArrayList<>();
        projects.add(new Project(Long.parseLong("1"),
                "Fibbonaccijev niz",
                "Niz gdje je svaki iduci broj pomnozen prethodnim",
                LocalDate.now(),
                users.get(0),
                Subject.DUBOKO_UCENJE,
                commentsData()
                ));
        projects.add(new Project(Long.parseLong("1"),
                "Euklidov algoritam",
                "algoritam",
                LocalDate.now(),
                users.get(1),
                Subject.PROGRAMIRANJE,
                commentsData()
        ));
        projects.add(new Project(Long.parseLong("1"),
                "projekt1",
                "opis1",
                LocalDate.now(),
                users.get(2),
                Subject.PROGRAMIRANJE_U_JEZIKU_JAVA,
                commentsData()
        ));
        projects.add(new Project(Long.parseLong("1"),
                "projekt2",
                "opis2",
                LocalDate.now(),
                users.get(3),
                Subject.OBJEKTNO_ORIJENTIRANO_PROGRAMIRANJE,
                commentsData()
        ));

        return projects;
    }

    private static List<Comment> commentsData() {
        List<User> users = DatabaseUtilUsers.getUsersList();


        List<Comment> commentsList = new ArrayList<>();

        commentsList.add(new Comment(users.get(0),"""
                Moje rjesenje za Fibbonacijev niz
                public void initialize(Project project){
                                
                        titleLabel.setText(project.getProjectName());
                        descriptionLabel.setText(project.getProjectDescription());
                        authorLabel.setText(project.getProjectOwner().getFname() + " " + project.getProjectOwner().getLname());
                    }           
                """,10));

        commentsList.add(new Comment(users.get(1),"""
                Mene je to također mučilo pa pogledaj ovaj video:        
                https://www.youtube.com/watch?v=NHPLjIkrNvI&ab_channel=MahmoudHamwi
                """,2));

        commentsList.add(new Comment(users.get(2),"""
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
                """,15));

        commentsList.add(new Comment(users.get(3),"""
                Samo radi sve ce se to vratit s kamatama,
                svidja mi se rjesenje od Josipa.
                """,7));


        return commentsList;
    }


}
