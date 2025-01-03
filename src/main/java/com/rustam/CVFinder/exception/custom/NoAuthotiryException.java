package com.rustam.CVFinder.exception.custom;

import lombok.Getter;

@Getter
public class NoAuthotiryException extends RuntimeException {

    private final String message;

    public NoAuthotiryException(String message) {
        super(message);
        this.message = message;
    }
}
