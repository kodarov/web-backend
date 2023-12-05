package ru.skypro.homework.exception;

public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException(){
        super("User is not authorized to perform this operation");
    }
    public UnauthorizedUserException(String message){
        super(message);
    }
    public UnauthorizedUserException(String message, Throwable cause){
        super(message,cause);
    }
}
