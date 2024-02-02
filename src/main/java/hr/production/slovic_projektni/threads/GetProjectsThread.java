package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.model.Project;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetProjectsThread extends DatabaseThread implements Runnable{

    private List<Project> projectList;

    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            this.projectList = (super.getProjectsFromDatabase());
            System.out.println("Nit se izvodi.");
        }).join();
    }

    public List<Project> getProjectList() {
        return projectList;
    }

}
