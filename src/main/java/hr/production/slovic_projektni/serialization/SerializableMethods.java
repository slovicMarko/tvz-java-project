package hr.production.slovic_projektni.serialization;

import hr.production.slovic_projektni.model.Comment;
import hr.production.slovic_projektni.model.Project;
import hr.production.slovic_projektni.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface SerializableMethods {

    String SERIALIZABLE_FILE_NAME = "data/changes.dat";

    static <T> void serializeToFile(SerializableObject<T> serializableObject) {
        //List<SerializableObject<T>> serializableObjects = new ArrayList<>();
        List<SerializableObject<T>> serializableObjects = SerializableMethods.deserializeFromFile();
        serializableObjects.add(serializableObject);

        try(FileOutputStream fileOutputStream = new FileOutputStream(SERIALIZABLE_FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeObject(serializableObjects);
            System.out.println("Serialization successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    static <T> void serializeToFileCorrect() {
        SerializableObject<T> serializableObject = null;
        List<SerializableObject<T>> serializableObjects = SerializableMethods.deserializeFromFile();
        for (SerializableObject<T> s : serializableObjects){
            if (s.getChanged() instanceof Project){
                serializableObject = s;
            }
        }
        if (serializableObject != null){
            serializableObjects.remove(serializableObject);
        }

        try(FileOutputStream fileOutputStream = new FileOutputStream(SERIALIZABLE_FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeObject(serializableObjects);
            System.out.println("Serialization successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

    static <T> List<SerializableObject<T>> deserializeFromFile() {

        try(FileInputStream fileInputStream = new FileInputStream(SERIALIZABLE_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){

            @SuppressWarnings("unchecked")
            List<SerializableObject<T>> serializedObjects = (List<SerializableObject<T>>) objectInputStream.readObject();

            return serializedObjects;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
