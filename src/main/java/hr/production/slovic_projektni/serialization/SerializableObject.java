package hr.production.slovic_projektni.serialization;

import hr.production.slovic_projektni.MainApplication;
import hr.production.slovic_projektni.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class SerializableObject<T> implements Serializable {

    private T original;
    private T changed;
    private User madeChange;
    private LocalDateTime changeTime;

    public static class Builder<T> {
        private T original;
        private T changed;
        private final User madeChange = MainApplication.getActiveUser();
        private final LocalDateTime changeTime = LocalDateTime.now();

        public Builder(T original) {
            this.original = original;
        }

        public Builder withChangedClass(T changed){
            this.changed = changed;
            return this;
        }

        public SerializableObject<T> build(){
            SerializableObject<T> serializableObject = new SerializableObject<>();
            serializableObject.original = this.original;
            serializableObject.changed = this.changed;
            serializableObject.madeChange = this.madeChange;
            serializableObject.changeTime = this.changeTime;
            return serializableObject;
        }
    }

    public T getOriginal() {
        return original;
    }

    public void setOriginal(T original) {
        this.original = original;
    }

    public T getChanged() {
        return changed;
    }

    public void setChanged(T changed) {
        this.changed = changed;
    }

    public User getMadeChange() {
        return madeChange;
    }

    public void setMadeChange(User madeChange) {
        this.madeChange = madeChange;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializableObject<?> that = (SerializableObject<?>) o;
        return Objects.equals(original, that.original) && Objects.equals(changed, that.changed) && Objects.equals(madeChange, that.madeChange) && Objects.equals(changeTime, that.changeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, changed, madeChange, changeTime);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
