package lsit.Exceptions;
/**
 * Exception thrown when an input is invalid.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}