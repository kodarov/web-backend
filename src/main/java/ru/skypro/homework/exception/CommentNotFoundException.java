package ru.skypro.homework.exception;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException() {
        super("Comment not found!");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }

}
