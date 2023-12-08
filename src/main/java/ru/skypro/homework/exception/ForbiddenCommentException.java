package ru.skypro.homework.exception;

public class ForbiddenCommentException extends RuntimeException {
    public ForbiddenCommentException() {
        super("Forbidden comment!");
    }

    public ForbiddenCommentException(String message) {
        super(message);
    }
}
