package ru.skypro.homework.exception;

public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException() {
        super("Ad not found!");
    }

    public AdNotFoundException(String message) {
        super(message);
    }
}
