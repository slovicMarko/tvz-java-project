package hr.production.slovic_projektni.exception;



public class FxmlLoadException extends RuntimeException {
    public FxmlLoadException() {
    }

    public FxmlLoadException(String message) {
        super(message);
    }

    public FxmlLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FxmlLoadException(Throwable cause) {
        super(cause);

    }
}
