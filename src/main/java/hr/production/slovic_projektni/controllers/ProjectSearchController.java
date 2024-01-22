package hr.production.slovic_projektni.controllers;


import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.ProjectView;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.utils.FileUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectSearchController {

    @FXML TableView<Project> projectTableView;
    @FXML TableColumn<Project, String> titleTableColumn;
    @FXML TableColumn<Project, String> descriptionTableColumn;
    @FXML TableColumn<Project, String> subjectTableColumn;
    @FXML TableColumn<Project, String> professorTableColumn;
    @FXML TableColumn<Project, String> postingDateTableColumn;
    @FXML TableColumn<Project, String> likesTableColumn;
    @FXML TableColumn<Project, String> dislikesTableColumn;
    @FXML ChoiceBox<String> subjectFilterChoiceBox;
    @FXML ChoiceBox<String> professorFilterChoiceBox;

    public void initialize(){

        setChoiceBoxes();

        List<Project> projects = FileUtils.getProjects();
        ObservableList<Project> observableProjectsList = FXCollections.observableArrayList(projects);

        projectTableView.setItems(observableProjectsList);
        initializeTableColumns();

        projectTableView.setOnMouseClicked((event)-> {
            if (event.getClickCount() == 2){
                Project selectedProject = projectTableView.getSelectionModel().getSelectedItem();

                ProjectView.showProjectView(selectedProject);
            }
        });

    }

    public void applyButton(){
        System.out.println(professorFilterChoiceBox.getValue());
        List<Subject> subjects = Arrays.stream(Subject.values()).toList();

        List<Subject> filteredSubjects = subjects.stream()
                .filter(subject -> subject.getProfessorName()==professorFilterChoiceBox.getValue())
                .collect(Collectors.toList());

        for (Subject subject : filteredSubjects){
            System.out.println(subject.getName());
        }

    }


    private void setChoiceBoxes() {
        List<Subject> subjects = Arrays.stream(Subject.values()).toList();
        List<String> subjectNames = subjects.stream()
                .map(Subject::getName)
                .collect(Collectors.toList());

        List<String> professorNames = new ArrayList<>();

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
                LocalDate postingDate = param.getValue().getStartDate();
                String postingDateString = postingDate.format(Constants.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(postingDateString);
            }
        });
    }


}
