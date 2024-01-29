package hr.production.slovic_projektni.exception;

public class ClickedOnInvalidContentException extends RuntimeException{

    public ClickedOnInvalidContentException() {
    }

    public ClickedOnInvalidContentException(String message) {
        super(message);
    }

    public ClickedOnInvalidContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClickedOnInvalidContentException(Throwable cause) {
        super(cause);
    }
}
