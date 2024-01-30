package hr.production.slovic_projektni.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Project extends NamedEntity implements Serializable, Cloneable {
    private String description;
    private LocalDate startDate;
    private User author;
    private Subject subject;
    private List<Comment> comments;

    public Project(Long id, String name, String description, LocalDate startDate, User author, Subject subject, List<Comment> comments) {
        super(id, name);
        this.description = description;
        this.startDate = startDate;
        this.author = author;
        this.subject = subject;
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Project project = (Project) o;
        return Objects.equals(description, project.description) && Objects.equals(startDate, project.startDate) && Objects.equals(author, project.author) && subject == project.subject && Objects.equals(comments, project.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, startDate, author, subject, comments);
    }

    @Override
    public Project clone() {
        try {
            Project clone = (Project) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
