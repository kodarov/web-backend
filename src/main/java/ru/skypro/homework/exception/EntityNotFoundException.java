package ru.skypro.homework.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(){
        super("Entity not found");
    }
    public EntityNotFoundException(String message){
        super(message);
    }
    public EntityNotFoundException(String message,Throwable cause) {
        super(message, cause);
    }
}
