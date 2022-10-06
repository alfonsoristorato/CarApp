package com.alfonso.CarApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(AttributeMissingException.class)
//    public ResponseEntity<Object> handleAttributeMissingException(
//            AttributeMissingException ex, WebRequest request) {
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("description", "Incorrect car data provided");
//
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        Map<String, Object> errorBody = new HashMap<>();

        if (!violations.isEmpty()) {


            violations.forEach(violation -> {

                if(violation.getMessage().equals("must not be empty")){

                    errorBody.put("description", "Incorrect car data provided");
                }
            });

        } else {
            errorBody.put("description", "ConstraintViolationException occurred.");
        }
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
