package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.serialization.SerializableMethods;
import hr.production.slovic_projektni.serialization.SerializableObject;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetSerializableDataThread<T> extends SerializableThread implements Runnable{

    private List<SerializableObject<T>> serializableObjects;

    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            this.serializableObjects = super.getSerializableObjects();
            System.out.println("Nit se izvodi. Serializable");
        }).join();
    }

    public List<SerializableObject<T>> getSerializedList() {
        return serializableObjects;
    }
}
