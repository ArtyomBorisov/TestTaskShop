package com.example.shop.controller.advice;

import com.example.shop.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationHandler(ValidationException e) {
        return new ResponseEntity<>(
                new ResponseError("error", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return new ResponseEntity<>(
                new ResponseError("error",
                        "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
