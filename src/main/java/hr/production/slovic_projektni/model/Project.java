package hr.production.slovic_projektni.model;

import java.time.LocalDate;
import java.util.List;

public class Project extends NamedEntity {
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
}
