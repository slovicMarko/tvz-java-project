package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.DateAndTime;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.threads.SetSerializableDataThread;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


public class NewProjectController implements CustomInitializable {

    @FXML private TextArea projectDescriptionTextArea;
    @FXML private TextField projectNameTextField;
    @FXML private ChoiceBox<String> subjectChoiceBox;
    @FXML private Button confirmButton;
    @FXML private Label titleLabel;

    private Subject selectedSubject;
    private static Project projectInProgress;


    public void initialize(){
        subjectChoiceBox.setItems(FXCollections.observableArrayList(Subject.getAllSubjects()));
        subjectChoiceBox.setOnAction(event -> selectedSubject = Subject.findEnumByName(subjectChoiceBox.getValue()));
    }

    public <T> void initialize(T project){
        titleLabel.setText("Edit project");
        confirmButton.setText("Save changes");

        projectInProgress = (Project) project;
        projectNameTextField.setText(((Project) project).getName());
        projectDescriptionTextArea.setText(((Project) project).getDescription());
        subjectChoiceBox.setValue(((Project) project).getSubject().getName());

    }

    public void cancelButton(){
        NavigationMethods.goToProjectSearchPage();
    }

    public void addNewProjectButton(){

        try{
            if (Optional.of(projectInProgress).isPresent()){
                Alert alert = Constants.confirmAlert(null, "Are you sure you want to make new project?");
                alert.setContentText("Are you sure you want to make new project?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK){

                        Project oldVersion = projectInProgress.clone();
                        projectInProgress.setName(projectNameTextField.getText());
                        projectInProgress.setDescription(projectDescriptionTextArea.getText());
                        projectInProgress.setSubject(Subject.findEnumByName(subjectChoiceBox.getValue()));

                        SerializableObject<Project> projectSerializableObject = new SerializableObject.Builder<>(oldVersion)
                                .withChangedClass(projectInProgress).build();

                        SetSerializableDataThread<Project> setSerializableDataThread = new SetSerializableDataThread<>(projectSerializableObject);
                        setSerializableDataThread.run();

                        SerializableMethods.serializeToFile(projectSerializableObject);
                        DatabaseUtilProject.updateProject(projectInProgress);
                        NavigationMethods.goToProjectSearchPage();
                    }
                });
            }
        } catch (NullPointerException e){

            if (projectNameTextField.getText().isEmpty() ||
                    projectDescriptionTextArea.getText().isEmpty() ||
                    subjectChoiceBox.getValue() == null){
                Constants.errorAlert("Invalid input", "There are some empty input fields.");
            } else {
                Alert alert = Constants.confirmAlert(null, "Are you sure you want to make new project?");
                alert.setContentText("Are you sure you want to make new project?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK){
                        Project newProject = new Project(Long.parseLong("0"),
                                projectNameTextField.getText(),
                                projectDescriptionTextArea.getText(),
                                new DateAndTime(LocalDateTime.now()),
                                MainApplication.getActiveUser(),
                                selectedSubject,
                                new ArrayList<>());

                        SerializableObject<Project> projectSerializableObject = new SerializableObject.Builder<>(new Project())
                                .withChangedClass(newProject).build();

                        SetSerializableDataThread<Project> setSerializableDataThread = new SetSerializableDataThread<>(projectSerializableObject);
                        setSerializableDataThread.run();

                        DatabaseUtilProject.saveProject(newProject);
                        NavigationMethods.goToProjectSearchPage();
                    }
                });
            }
        }
    }
}
