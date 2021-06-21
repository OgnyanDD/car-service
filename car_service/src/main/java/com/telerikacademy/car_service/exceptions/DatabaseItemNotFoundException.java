package com.telerikacademy.car_service.exceptions;

import java.util.NoSuchElementException;

public class DatabaseItemNotFoundException extends NoSuchElementException {
    public DatabaseItemNotFoundException(String message) {

        super(String.format("%s", message));
    }
}
