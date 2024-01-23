package hr.production.slovic_projektni.controllers;

import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Subject;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class NewProjectController {

    @FXML private TextArea projectDescriptionTextArea;
    @FXML private TextField projectNameTextField;
    @FXML private ChoiceBox<String> subjectChoiceBox;

    private Subject selectedSubject;


    public void initialize(){
        subjectChoiceBox.setItems(FXCollections.observableArrayList(Subject.getAllSubjects()));
        subjectChoiceBox.setOnAction(event -> selectedSubject = Subject.findEnumByName(subjectChoiceBox.getValue()));
    }

    public void cancelButton(){
        NavigationMethods.goToProjectSearchPage();
    }

    public void addNewProjectButton(){
        DatabaseUtilProject.saveProject(
                projectNameTextField.getText(),
                projectDescriptionTextArea.getText(),
                selectedSubject
        );
        NavigationMethods.goToProjectSearchPage();
    }


}
