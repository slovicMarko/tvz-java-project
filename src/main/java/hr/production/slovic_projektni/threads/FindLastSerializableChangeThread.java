package hr.production.slovic_projektni.threads;

import hr.production.slovic_projektni.serialization.SerializableObject;

import java.util.concurrent.CompletableFuture;

public class FindLastSerializableChangeThread<T> extends SerializableThread implements Runnable{

    SerializableObject<T> lastData;
    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            this.lastData = (SerializableObject<T>) super.getSerializableObjects().reversed().get(0);
        }).join();
    }

    public SerializableObject<T> getLastData() {
        return lastData;
    }
}
