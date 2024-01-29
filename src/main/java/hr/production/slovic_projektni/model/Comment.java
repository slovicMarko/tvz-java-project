package hr.production.slovic_projektni.model;


import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.main.Main;

import java.util.List;

public non-sealed class Comment extends NamedEntity implements LikedComment {

    private final User author;
    private String content;
    List<Long> likes;

    public Comment(Long id, User author, String content, List<Long> likes) {
        super(id);
        this.author = author;
        this.content = content;
        this.likes = likes;
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
}
