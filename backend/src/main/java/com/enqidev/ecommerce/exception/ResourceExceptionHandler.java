package com.enqidev.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // handle exceptions globally across multiple controllers
public class ResourceExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException
            (ResourceNotFoundException resourceNotFoundException) {
        ResourceException resourceException = new ResourceException(
                resourceNotFoundException.getMessage(),
                resourceNotFoundException.getCause(),
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(resourceException, HttpStatus.NOT_FOUND);
    }

}
