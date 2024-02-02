package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;

import java.util.List;

public abstract class SerializableThread {

    public synchronized <T> List<SerializableObject<T>> getSerializableObjects(){
        return SerializableMethods.deserializeFromFile();
    }

    public synchronized <T> void saveToSerializableFile(SerializableObject<T> serializableObject){

        SerializableMethods.serializeToFile(serializableObject);
    }

}
