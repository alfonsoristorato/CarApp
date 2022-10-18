package com.alfonso.CarApp.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationErrorHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity handleMethodArgumentNotValidException(
            HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("description", "Incorrect car data provided");
        return new ResponseEntity<>(body, HttpStatus.valueOf(400));
    }


}
