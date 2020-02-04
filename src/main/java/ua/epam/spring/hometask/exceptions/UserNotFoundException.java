package ua.epam.spring.hometask.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
