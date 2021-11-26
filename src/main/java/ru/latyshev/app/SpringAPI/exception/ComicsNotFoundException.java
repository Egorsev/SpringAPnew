package ru.latyshev.app.SpringAPI.exception;

public class ComicsNotFoundException extends Exception {

    public ComicsNotFoundException(String message) {
        super(message);
    }
}
