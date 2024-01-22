package hr.production.slovic_projektni.utils;

import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    public static List<Project> getProjects(){
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(Long.parseLong("1"),
                Long.parseLong("1"),
                "Fibbonaccijev niz",
                "Niz gdje je svaki iduci broj pomnozen prethodnim",
                new Student("Marko", "Slovic"),
                LocalDate.now()));

        projects.add(new Project(Long.parseLong("1"),
                Long.parseLong("1"),
                "Euklidov algoritam",
                "algoritam",
                new Student("Pero", "Perić"),
                LocalDate.now()));

        projects.add(new Project(Long.parseLong("1"),
                Long.parseLong("1"),
                "projekt1",
                "projekt1",
                new Student("Ivan", "Ivić"),
                LocalDate.now()));

        projects.add(new Project(Long.parseLong("1"),
                Long.parseLong("1"),
                "projekt2",
                "projekt2",
                new Student("Marko", "Markić"),
                LocalDate.now()));




        return projects;
    }


}
