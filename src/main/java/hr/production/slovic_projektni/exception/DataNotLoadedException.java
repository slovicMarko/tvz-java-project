package hr.production.slovic_projektni.exception;

import java.io.IOException;

public class DataNotLoadedException extends RuntimeException {
    public DataNotLoadedException() {
    }

    public DataNotLoadedException(String message) {
        super(message);
    }

    public DataNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotLoadedException(Throwable cause) {
        super(cause);
    }
}
