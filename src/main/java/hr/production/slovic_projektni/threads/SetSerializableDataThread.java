package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;

import java.util.concurrent.CompletableFuture;

public class SetSerializableDataThread<T> extends SerializableThread implements Runnable{

    private SerializableObject<T> serializableObject;

    public SetSerializableDataThread(SerializableObject<T> serializableObject) {
        this.serializableObject = serializableObject;
    }

    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            super.saveToSerializableFile(serializableObject);
            System.out.println("Saved to serializable file!");
        }).join();
    }

}
