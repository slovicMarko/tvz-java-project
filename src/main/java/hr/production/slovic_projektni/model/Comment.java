package hr.production.slovic_projektni.model;


import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.main.Main;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public non-sealed class Comment extends NamedEntity implements LikedComment, Serializable, Cloneable {

    private User author;
    private String content;
    private DateAndTime postDate;
    List<Long> likes;

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
        this.likes.add(MainApplication.activeUser.getId());
    }

    @Override
    public void submitDislike() {
        this.likes.remove(MainApplication.activeUser.getId());
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
            throw new AssertionError();
        }
    }
}
