package hr.production.slovic_projektni.sort;

import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;

import java.util.Comparator;

public class DateSorter<T> implements Comparator<T> {

    private Boolean ascending;

    public DateSorter(Boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(T p1, T p2) {
        if (ascending) {
            if (p1 instanceof Project && p2 instanceof Project) {
                return ((Project) p1).getPostDate().postDate().compareTo(((Project) p2).getPostDate().postDate());
            } else if (p1 instanceof Comment && p2 instanceof Comment) {
                return ((Comment) p1).getPostDate().postDate().compareTo(((Comment) p2).getPostDate().postDate());
            }
        } else {
            if (p2 instanceof Project && p1 instanceof Project) {
                return ((Project) p2).getPostDate().postDate().compareTo(((Project) p1).getPostDate().postDate());
            } else if (p2 instanceof Comment && p1 instanceof Comment) {
                return ((Comment) p2).getPostDate().postDate().compareTo(((Comment) p1).getPostDate().postDate());
            }
        }
        return 0;
    }

}
