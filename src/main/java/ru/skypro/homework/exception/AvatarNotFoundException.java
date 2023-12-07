package ru.skypro.homework.exception;

public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException() {
        super("Avatar not found!");
    }

    public AvatarNotFoundException(String message) {
        super(message);
    }
}
