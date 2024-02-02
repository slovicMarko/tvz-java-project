package hr.production.slovic_projektni.model;


import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public non-sealed class Comment extends NamedEntity implements LikedComment, Serializable, Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(Comment.class);
    private User author;
    private String content;
    private DateAndTime postDate;
    private List<Long> likes;

    public Comment(Long id, User author, String content, DateAndTime postDate, List<Long> likes) {
        super(id);
        this.author = author;
        this.content = content;
        this.postDate = postDate;
        this.likes = likes;
    }

    public Comment() {
        super(Long.parseLong("1"));
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateAndTime getPostDate() {
        return postDate;
    }

    public List<Long> getLikes() {
        return likes;
    }

    @Override
    public Integer getNumberOfLikes() {
        return likes.size();
    }

    @Override
    public void submitLike() {
        this.likes.add(MainApplication.getActiveUser().getId());
    }

    @Override
    public void submitDislike() {
        this.likes.remove(MainApplication.getActiveUser().getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(author, comment.author) && Objects.equals(content, comment.content) && Objects.equals(postDate, comment.postDate) && Objects.equals(likes, comment.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, content, postDate, likes);
    }

    @Override
    public Comment clone() {
        try {
            return (Comment) super.clone();
        } catch (CloneNotSupportedException e) {
            String message = "Error while cloning comment for serialization";
            logger.error(message);
            Constants.errorAlert("Cloning error", message);
            throw new AssertionError();
        }
    }
}
