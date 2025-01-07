package ro.ubbcluj.tpjad.jadbackend.exceptions.auth;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password mismatch");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }
}
