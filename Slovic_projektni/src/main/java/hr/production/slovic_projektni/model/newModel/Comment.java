package hr.production.slovic_projektni.model.newModel;

import hr.production.slovic_projektni.model.Person;

public record Comment(Person author, String content, Integer likes) implements LikedComment {
    @Override
    public Integer getLikes() {
        return likes;
    }

    @Override
    public Comment submitLike() {
        return new Comment(author, content, likes+1);
    }
}
