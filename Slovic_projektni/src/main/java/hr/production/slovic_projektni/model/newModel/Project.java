package hr.production.slovic_projektni.model.newModel;

import hr.production.slovic_projektni.model.Person;

import java.time.LocalDate;
import java.util.List;

public class Project extends NamedEntity {
    private String description;
    private LocalDate startDate;
    private User author;  // POGLEDATI KOJI KORISTITI
    private Long authorId;
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

    public Project(Long id, String name, String description, LocalDate startDate, Long authorId, Subject subject, List<Comment> comments) {
        super(id, name);
        this.description = description;
        this.startDate = startDate;
        this.authorId = authorId;
        this.subject = subject;
        this.comments = comments;
    }
}
