package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.model.Comment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetCommentsThread extends DatabaseThread implements Runnable{

    private List<Comment> commentList;
    private Long projectId;

    public GetCommentsThread(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public void run() {
//        CompletableFuture.runAsync(() -> {
//
//        }).join();
        this.commentList = super.getCommentsFromDatabase(projectId, super.getUsersMapFromDatabase());
        System.out.println("Nit se izvodi. Comment");
    }

    public List<Comment> getCommentList(){
        return commentList;
    }
}
