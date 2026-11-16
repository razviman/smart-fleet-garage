package com.example.proiect_tpd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value()); // HTTP 400
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage()); // here we use ex.getMessage() to get the exception message
        body.put("path", request.getDescription(false).replace("uri=", ""));

        // return the json response with the error details and HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}