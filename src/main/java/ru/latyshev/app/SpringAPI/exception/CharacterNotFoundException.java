package ru.latyshev.app.SpringAPI.exception;

public class CharacterNotFoundException extends Exception{
    public CharacterNotFoundException(String message) {
        super(message);
    }
}
