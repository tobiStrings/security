package com.example.personalsecurity.exceptions;

public class SecException extends Exception{
    public SecException() {
    }

    public SecException(String message) {
        super(message);
    }

    public SecException(String message, Throwable cause) {
        super(message, cause);
    }
}
