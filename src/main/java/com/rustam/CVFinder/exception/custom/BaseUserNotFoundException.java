package com.rustam.CVFinder.exception.custom;

import lombok.Getter;

@Getter
public class BaseUserNotFoundException extends RuntimeException {

    private final String message;

    public BaseUserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
