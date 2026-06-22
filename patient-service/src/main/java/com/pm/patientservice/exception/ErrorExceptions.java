package com.pm.patientservice.exception;

import org.springframework.http.HttpStatus;

public class ErrorExceptions extends RuntimeException {

    private final HttpStatus httpStatus;
    
    public ErrorExceptions(String message, HttpStatus httpStatus) {
         super(message); 
         this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
