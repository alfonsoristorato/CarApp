package com.alfonso.CarApp.exception;

import com.mongodb.MongoWriteException;
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

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity duplicateKeyException(
            MongoWriteException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("description", "Car already exists");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity genericException(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        Map<String, Object> errorBody = new HashMap<>();

        if (!violations.isEmpty()) {


            violations.forEach(violation -> {
                System.out.println(violation);

                if(violation.getMessage().equals("must not be empty")){

                    errorBody.put("description", "Incorrect car data provided");
                }
            });

        } else {
            errorBody.put("description", "ConstraintViolationException occurred.");
        }
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity incorrectQueryParameterException(IllegalArgumentException illegalArgumentException) {

            Map<String, Object> body = new HashMap<>();
            if (illegalArgumentException.getMessage() == "No car matches"
                    || illegalArgumentException.getMessage() == "No id provided"
                    || illegalArgumentException.getMessage() == "Illegal car parameters") {
                body.put("description", "Incorrect car data provided");
            } else if (illegalArgumentException.getMessage() == "Incorrect Format Values") {
                body.put("description", "Incorrect query parameter provided");
            } else {
                body.put("description", "Generic IllegalArgumentException");

            }


            return new ResponseEntity<>(body, HttpStatus.valueOf(400));
    }





}
