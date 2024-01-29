package hr.production.slovic_projektni.model;

public sealed interface LikedComment permits Comment {
    Integer getNumberOfLikes();

    void submitLike();

    void submitDislike();


}
