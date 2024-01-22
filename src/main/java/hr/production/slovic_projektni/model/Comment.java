package hr.production.slovic_projektni.model;


public record Comment(User author, String content, Integer likes) implements LikedComment {

    @Override
    public User author() {
        return author;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public Integer getLikes() {
        return likes;
    }

    @Override
    public Comment submitLike() {
        return new Comment(author, content, likes+1);
    }
}
