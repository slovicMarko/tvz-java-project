package hr.production.slovic_projektni.model;

import hr.production.slovic_projektni.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Project extends NamedEntity implements Serializable, Cloneable {

    public static final Logger logger = LoggerFactory.getLogger(Project.class);
    private String description;
    private DateAndTime postDate;
    private User author;
    private Subject subject;
    private List<Comment> comments;

    public Project(Long id, String name, String description, DateAndTime postDate, User author, Subject subject, List<Comment> comments) {
        super(id, name);
        this.description = description;
        this.postDate = postDate;
        this.author = author;
        this.subject = subject;
        this.comments = comments;
    }

    public Project() {
        super(Long.parseLong("1"), "");
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public DateAndTime getPostDate() {
        return postDate;
    }
    public User getAuthor() {
        return author;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public List<Comment> getComments() {
        return comments;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Project project = (Project) o;
        return Objects.equals(description, project.description) && Objects.equals(postDate, project.postDate) && Objects.equals(author, project.author) && subject == project.subject && Objects.equals(comments, project.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, postDate, author, subject, comments);
    }

    @Override
    public Project clone() {
        try {
            return (Project) super.clone();
        } catch (CloneNotSupportedException e) {
            String message = "Error while cloning project for serialization";
            logger.error(message);
            Constants.errorAlert("Cloning error", message);
            throw new AssertionError();
        }
    }
}
