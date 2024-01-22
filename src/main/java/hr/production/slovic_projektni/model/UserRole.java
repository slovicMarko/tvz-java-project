package hr.production.slovic_projektni.model;

public enum UserRole {
    ADMIN("ADMIN"),
    PROFESSOR("PROFESSOR"),
    STUDENT("STUDENT");

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
