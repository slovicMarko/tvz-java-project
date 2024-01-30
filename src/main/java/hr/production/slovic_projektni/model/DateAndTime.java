package hr.production.slovic_projektni.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public record DateAndTime(LocalDateTime postDate) implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateAndTime that = (DateAndTime) o;
        return Objects.equals(postDate, that.postDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postDate);
    }
}
