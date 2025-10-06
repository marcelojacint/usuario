package com.marcelodev.usuario.infrastructure.exception;

public class ConflicException extends RuntimeException {

    public ConflicException(String message) {
        super(message);
    }

    public ConflicException(String message, Throwable cause) {
        super(message, cause);
    }
}
