package hr.production.slovic_projektni.model;

import java.time.LocalDateTime;
import java.util.List;

public class Student extends Person{

    private String JMBAG;

    public Student(String fname, String lname, String JMBAG) {
        super(fname, lname);
        this.JMBAG = JMBAG;
    }

    public Student(String JMBAG) {
        this.JMBAG = JMBAG;
    }

    public Student(String fname, String lname){
        super(fname, lname);
    }

    public Student(){
        super();
    }

    public String getJMBAG() {
        return JMBAG;
    }

    public void setJMBAG(String JMBAG) {
        this.JMBAG = JMBAG;
    }

}
