package com.telerikacademy.car_service.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateDatabaseItemFoundException extends DuplicateKeyException {
    public DuplicateDatabaseItemFoundException(String message) {

        super(String.format("%s", message));
    }
}
