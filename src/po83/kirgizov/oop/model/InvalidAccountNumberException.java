package po83.kirgizov.oop.model;

public class InvalidAccountNumberException extends RuntimeException {
    public InvalidAccountNumberException() {
    }

    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
