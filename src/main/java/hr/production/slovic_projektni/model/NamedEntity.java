package hr.production.slovic_projektni.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class NamedEntity implements Serializable {
    private Long id;
    private String name;

    public NamedEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public NamedEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedEntity that = (NamedEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
