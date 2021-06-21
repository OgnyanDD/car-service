package com.telerikacademy.car_service.controllers.exception_handler;

import com.telerikacademy.car_service.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.car_service.exceptions.DuplicateDatabaseItemFoundException;
import com.telerikacademy.car_service.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    @ExceptionHandler(DatabaseItemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorHandler onDatabaseItemNotFoundException(DatabaseItemNotFoundException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ExceptionHandler(DuplicateDatabaseItemFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorHandler onDuplicateDatabaseItemFoundException(DuplicateDatabaseItemFoundException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    ErrorHandler onWrongPasswordException(WrongPasswordException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        error.setMessage(e.getMessage());

        return error;
    }
}
