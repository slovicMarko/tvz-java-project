package hr.production.slovic_projektni.model;

public enum UserRole {
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
