package ru.skypro.homework.exception;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException() {
        super("Invalid image!");
    }

    public InvalidImageException(String message) {
        super(message);
    }
}
