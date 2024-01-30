package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to make changes?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK){

                        Project oldVersion = projectInProgress.clone();
                        projectInProgress.setName(projectNameTextField.getText());
                        projectInProgress.setDescription(projectDescriptionTextArea.getText());
                        projectInProgress.setSubject(Subject.findEnumByName(subjectChoiceBox.getValue()));
                        SerializableObject<Project> projectSerializableObject = new SerializableObject.Builder<>(oldVersion)
                                .withChangedClass(projectInProgress).build();

                        SerializableMethods.serializeToFile(projectSerializableObject);
                        DatabaseUtilProject.updateProject(projectInProgress);
                        NavigationMethods.goToProjectSearchPage();
                    }
                });
            }
        } catch (NullPointerException e){
            DatabaseUtilProject.saveProject(
                    projectNameTextField.getText(),
                    projectDescriptionTextArea.getText(),
                    selectedSubject
            );
            NavigationMethods.goToProjectSearchPage();
        }


    }

}
