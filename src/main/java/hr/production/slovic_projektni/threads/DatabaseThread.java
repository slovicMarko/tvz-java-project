package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import hr.production.slovic_projektni.sort.DateSorter;
import hr.production.slovic_projektni.utils.DatabaseUtilComment;
import hr.production.slovic_projektni.utils.DatabaseUtilProject;
import hr.production.slovic_projektni.utils.DatabaseUtilUsers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DatabaseThread {


    public synchronized List<Project> getProjectsFromDatabase(){

        return DatabaseUtilProject.getProjects().stream()
                .sorted(new DateSorter<Project>(true))
                .collect(Collectors.toList());
    }

}
