package ro.ubbcluj.tpjad.jadbackend.exceptions.entity;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(Long id, String entityName) {
        super(String.format("Entity '%s' with id '%d' not found.", entityName, id));
    }
}
