package ua.epam.spring.hometask.exceptions;

public class ItemAlreadyExistException extends RuntimeException {

    public ItemAlreadyExistException() {
        super();
    }

    public ItemAlreadyExistException(String message) {
        super(message);
    }

    public ItemAlreadyExistException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
