package com.enqidev.ecommerce.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResourceException {

    private final String message;

    private final Throwable cause;

    private final HttpStatus httpStatus;

    public ResourceException(String message, Throwable cause, HttpStatus httpStatus) {
        this.message = message;
        this.cause = cause;
        this.httpStatus = httpStatus;
    }

}
