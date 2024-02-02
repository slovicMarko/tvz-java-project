package hr.production.slovic_projektni.serialization;

import hr.production.slovic_projektni.constants.Constants;
import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface SerializableMethods {

    Logger logger = LoggerFactory.getLogger(SerializableMethods.class);
    String SERIALIZABLE_FILE_NAME = "data/changes.dat";

    static <T> void serializeToFile(SerializableObject<T> serializableObject) {
        //List<SerializableObject<T>> serializableObjects = new ArrayList<>(); //za prvu
        List<SerializableObject<T>> serializableObjects = SerializableMethods.deserializeFromFile();
        serializableObjects.add(serializableObject);

        try(FileOutputStream fileOutputStream = new FileOutputStream(SERIALIZABLE_FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeObject(serializableObjects);

        } catch (IOException e) {
            String message = "Error while serializing object.";
            logger.error(message);
        }
    }

    static <T> List<SerializableObject<T>> deserializeFromFile() {

        try(FileInputStream fileInputStream = new FileInputStream(SERIALIZABLE_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

            List<SerializableObject<T>> serializedObjects = (List<SerializableObject<T>>) objectInputStream.readObject();

            return serializedObjects;

        } catch (IOException | ClassNotFoundException e) {
            String message = "Error while deserializing object. " + e.getMessage();
            logger.error(message);
            throw new RuntimeException();
        }
    }

}
