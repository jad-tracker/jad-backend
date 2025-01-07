package ro.ubbcluj.tpjad.jadbackend.exceptions.auth;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("The given username already exists");
    }

    public ExistingUserException(String message) {
        super(message);
    }

    public ExistingUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingUserException(Throwable cause) {
        super(cause);
    }
}
