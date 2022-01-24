package project.educatum.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
