package com.rustam.CVFinder.dto.response.message;

import org.springframework.http.HttpStatus;

public interface ResponseMessages {

    String key();
    String message();
    HttpStatus status();
}
