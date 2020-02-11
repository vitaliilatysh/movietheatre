package ua.epam.spring.hometask.exceptions;

public class TicketAlreadyBookedException extends RuntimeException {

    public TicketAlreadyBookedException() {
        super();
    }

    public TicketAlreadyBookedException(String message) {
        super(message);
    }

    public TicketAlreadyBookedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
