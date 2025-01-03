package com.rustam.CVFinder.dto.response.message;

import org.springframework.http.HttpStatus;

public record ExceptionResponseMessages
        (String key,
         String message,
         HttpStatus status) implements ResponseMessages {

}
