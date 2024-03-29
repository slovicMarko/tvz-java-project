package hr.production.slovic_projektni.model;

import java.io.Serializable;
import java.util.Objects;

public record Username(String username) implements Serializable {
    @Override
    public String username() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username1 = (Username) o;
        return Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


}
