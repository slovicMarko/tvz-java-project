package hr.production.slovic_projektni.controllers;


import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.ProjectView;
import hr.production.slovic_projektni.utils.FileUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.time.LocalDate;

import java.util.List;

public class ProjectSearchController {

    @FXML TableView<Project> projectTableView;
    @FXML TableColumn<Project, String> titleTableColumn;
    @FXML TableColumn<Project, String> descriptionTableColumn;
    @FXML TableColumn<Project, String> subjectTableColumn;
    @FXML TableColumn<Project, String> professorTableColumn;
    @FXML TableColumn<Project, String> postingDateTableColumn;
    @FXML TableColumn<Project, String> likesTableColumn;
    @FXML TableColumn<Project, String> dislikesTableColumn;

    public void initialize(){

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

    private void initializeTableColumns(){

        titleTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProjectName());
            }
        });
        descriptionTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProjectDescription());
            }
        });
        subjectTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProjectSubject());
            }
        });
        professorTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProjectProfessor());
            }
        });
        postingDateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                LocalDate postingDate = param.getValue().getDateOfPosting();
                String postingDateString = postingDate.format(Constants.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(postingDateString);
            }
        });

        likesTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getNumberOfLikes().toString());
            }
        });
        dislikesTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getNumberOfDislikes().toString());
            }
        });

    }


}
