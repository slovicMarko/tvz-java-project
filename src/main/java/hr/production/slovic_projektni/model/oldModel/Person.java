package hr.production.slovic_projektni.model.oldModel;

import java.util.List;

public abstract class Person {

    private Long Id;
    private String fname;
    private String lname;
    private String username;
    private String password;
    private Contact contact;
    private List<Long> projectIDs;




    public Person(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
    }

    public Person() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Long> getProjectIDs() {
        return projectIDs;
    }

    public void setProjectIDs(List<Long> projectIDs) {
        this.projectIDs = projectIDs;
    }
}
