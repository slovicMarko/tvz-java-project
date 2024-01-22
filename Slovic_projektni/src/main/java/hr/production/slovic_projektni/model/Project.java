package hr.production.slovic_projektni.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Project {
    private Long Id;
    private Long ownerId;
    private String projectName;
    private String projectDescription;
    private String projectSubject = "PUJ";
    private String projectProfessor = "A. Radovan";
    private Person projectOwner;
    private LocalDate dateOfPosting;
    private Integer numberOfLikes = 0;
    private Integer numberOfDislikes = 0;

    public Project(Long id, Long ownerId, String projectName, String projectDescription, Person projectOwner, LocalDate dateOfPosting) {
        Id = id;
        this.ownerId = ownerId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectOwner = projectOwner;
        this.dateOfPosting = dateOfPosting;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Person getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Person projectOwner) {
        this.projectOwner = projectOwner;
    }

    public LocalDate getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(LocalDate dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public Long getOwnerId() {

        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Integer getNumberOfDislikes() {
        return numberOfDislikes;
    }

    public void setNumberOfDislikes(Integer numberOfDislikes) {
        this.numberOfDislikes = numberOfDislikes;
    }

    public String getProjectSubject() {
        return projectSubject;
    }

    public void setProjectSubject(String projectSubject) {
        this.projectSubject = projectSubject;
    }

    public String getProjectProfessor() {
        return projectProfessor;
    }

    public void setProjectProfessor(String projectProfessor) {
        this.projectProfessor = projectProfessor;
    }

    public Boolean isHighPriority(){
        return true;
    }


}
