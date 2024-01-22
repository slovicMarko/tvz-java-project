package hr.production.slovic_projektni.sort;

import hr.production.slovic_projektni.model.Comment;

import java.util.Comparator;

public class CommentSorter implements Comparator<Comment> {

    @Override
    public int compare(Comment firstComment, Comment secondComment) {
        return secondComment.getLikes().compareTo(firstComment.getLikes());
    }
}
