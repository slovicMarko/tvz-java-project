package hr.production.slovic_projektni.model.newModel;

public sealed interface LikedComment extends Liked permits Comment {
    Integer getLikes();
    Comment submitLike();


}
