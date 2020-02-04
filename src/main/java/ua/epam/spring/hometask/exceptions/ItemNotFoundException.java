package ua.epam.spring.hometask.exceptions;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
