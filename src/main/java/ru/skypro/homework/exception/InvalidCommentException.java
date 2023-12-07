package ru.skypro.homework.exception;

public class InvalidCommentException extends RuntimeException {
    public InvalidCommentException() {
        super("Invalid comment!");
    }

    public InvalidCommentException(String message) {
        super(message);
    }
}
