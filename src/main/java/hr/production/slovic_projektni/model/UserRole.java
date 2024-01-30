package hr.production.slovic_projektni.model;

import java.io.Serializable;

public enum UserRole implements Serializable {
    ADMIN("Admin"),
    PROFESSOR("Professor"),
    STUDENT("Student");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
