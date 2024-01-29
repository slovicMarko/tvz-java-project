package hr.production.slovic_projektni.exception;

public class UserIsNotPresentException extends Exception {

    public UserIsNotPresentException() {
    }

    public UserIsNotPresentException(String message) {
        super(message);
    }

    public UserIsNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsNotPresentException(Throwable cause) {
        super(cause);
    }
}
