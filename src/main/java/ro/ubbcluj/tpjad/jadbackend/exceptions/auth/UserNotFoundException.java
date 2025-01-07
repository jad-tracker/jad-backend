package ro.ubbcluj.tpjad.jadbackend.exceptions.auth;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' not found.", username));
    }
}
