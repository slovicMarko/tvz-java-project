package hr.production.slovic_projektni.controllers;


import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.exception.ClickedOnInvalidContentException;
import hr.production.slovic_projektni.exception.DataNotLoadedException;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.sort.DateSorter;
import hr.production.slovic_projektni.threads.GetProjectsThread;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProjectSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectSearchController.class);

    @FXML TableView<Project> projectTableView;
    @FXML TableColumn<Project, String> titleTableColumn;
    @FXML TableColumn<Project, String> descriptionTableColumn;
    @FXML TableColumn<Project, String> subjectTableColumn;
    @FXML TableColumn<Project, String> professorTableColumn;
    @FXML TableColumn<Project, String> postingDateTableColumn;
    @FXML TableColumn<Project, String> commentsTableColumn;
    @FXML ChoiceBox<String> subjectFilterChoiceBox;
    @FXML ChoiceBox<String> professorFilterChoiceBox;
    @FXML TextField projectSearchTextField;

    private GetProjectsThread getProjectsThread;
    private static ScheduledExecutorService executorService;
    List<Project> projectsList, filteredList = new ArrayList<>();

    public void initialize(){

        setChoiceBoxes();

        projectsList = DatabaseUtilProject.getProjects().stream().sorted(new DateSorter<Project>(true)).collect(Collectors.toList());
        projectTableView.setItems(FXCollections.observableArrayList(projectsList));

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                if (!MainApplication.getFxmlName().equals("project-search.fxml")) {
                    executorService.shutdown();
                } else {
                    getProjectsThread = new GetProjectsThread();
                    getProjectsThread.run();

                    List<Project> updatedProjects = getProjectsThread.getProjectList();

                    if (!projectsList.equals(updatedProjects)) {
                        projectsList = updatedProjects;
                        projectTableView.setItems(FXCollections.observableArrayList(projectsList));
                    }
                }
            });
        }, 0, 10, TimeUnit.SECONDS);

        initializeTableColumns();

        projectTableView.setOnMouseClicked((event)-> {
            try{
                if (event.getClickCount() == 2){
                    selectingClickedProject();
                }
            } catch (ClickedOnInvalidContentException ex){
                logger.error(ex.getMessage());
                System.out.println(ex.getMessage());
            }
        });

        projectSearchTextField.setOnKeyTyped(event -> {
            try{
                filteredList = projectsList.stream()
                        .filter(project ->
                            projectSearchTextField.getText().isEmpty() ||
                                    project.getName().toLowerCase().contains(projectSearchTextField.getText().toLowerCase()) ||
                                    project.getDescription().toLowerCase().contains(projectSearchTextField.getText().toLowerCase()))
                        .collect(Collectors.toList());
                projectTableView.setItems(FXCollections.observableArrayList(filteredList));
            } catch (DataNotLoadedException e){
                logger.error("Data is not load.");
            }
        });
    }

    private void selectingClickedProject() {
        Project selectedProject = projectTableView.getSelectionModel().getSelectedItem();
        if (selectedProject != null){
            NavigationMethods.showProjectView(selectedProject);
        } else {
            throw new ClickedOnInvalidContentException("Clicked outside of the projects table.");
        }
    }

    public void applyButton(){
        List<Project> projects = DatabaseUtilProject.getProjects();
        List<Subject> subjects = Arrays.stream(Subject.values()).toList();

        List<Subject> filteredSubjects = subjects.stream()
                .filter(subject -> professorFilterChoiceBox.getValue()== null ||
                        Objects.equals(professorFilterChoiceBox.getValue(), " Default") ||
                        Objects.equals(subject.getProfessorName(), professorFilterChoiceBox.getValue()))
                .filter(subject -> subjectFilterChoiceBox.getValue()==null ||
                        Objects.equals(subjectFilterChoiceBox.getValue(), " Default") ||
                        Objects.equals(subject.getName(), subjectFilterChoiceBox.getValue()))
                .toList();

        List<Project> filteredProjects =  projects.stream()
                .filter(project -> filteredSubjects.contains(project.getSubject()))
                .collect(Collectors.toList());

        projectTableView.setItems(FXCollections.observableArrayList(filteredProjects));

    }

    private void setChoiceBoxes() {
        List<Subject> subjects = Arrays.stream(Subject.values()).toList();
        List<String> subjectNames = subjects.stream()
                .map(Subject::getName)
                .collect(Collectors.toList());

        subjectNames.add(0, " Default");
        List<String> professorNames = new ArrayList<>();
        professorNames.add(" Default");

        for (Subject subject : subjects){
            if (!professorNames.contains(subject.getProfessorName())){
                professorNames.add(subject.getProfessorName());
            }
        }
        subjectFilterChoiceBox.setItems(FXCollections.observableArrayList(subjectNames));
        professorFilterChoiceBox.setItems(FXCollections.observableArrayList(professorNames.stream().sorted().collect(Collectors.toList())));
    }

    private void initializeTableColumns(){

        titleTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });
        descriptionTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getDescription());
            }
        });
        subjectTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getSubject().getName());
            }
        });
        professorTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getSubject().getProfessorName());
            }
        });
        postingDateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                LocalDateTime postingDate = param.getValue().getPostDate().postDate();
                String postingDateString = postingDate.format(Constants.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(postingDateString);
            }
        });
        commentsTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(String.valueOf(param.getValue().getComments().size()));
            }
        });
    }
}
