package com.pm.patientservice.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pm.patientservice.dtos.ResponseDTO;
import com.pm.patientservice.helper.ResponseMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>>
     handlerValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(
            error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(ResponseMapper.failed(errors));
    }

    @ExceptionHandler(ErrorExceptions.class)
    public ResponseEntity<ResponseDTO<Void>> 
     handlerCustomException(ErrorExceptions ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(ResponseMapper.businesError(ex.getMessage()));
     }

}
