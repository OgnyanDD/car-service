package com.telerikacademy.car_service.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class WrongPasswordException extends BadCredentialsException {
    public WrongPasswordException(String message) {

        super(String.format("%s", message));
    }
}
