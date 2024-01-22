package hr.production.slovic_projektni.model;

import java.time.LocalDateTime;
import java.util.List;

public class Professor extends Person {

    private String kolegij;

    public Professor(String fname, String lname, String kolegij) {
        super(fname, lname);
        this.kolegij = kolegij;
    }

    public Professor(String kolegij) {
        this.kolegij = kolegij;
    }

    public String getKolegij() {
        return kolegij;
    }

    public void setKolegij(String kolegij) {
        this.kolegij = kolegij;
    }
}
