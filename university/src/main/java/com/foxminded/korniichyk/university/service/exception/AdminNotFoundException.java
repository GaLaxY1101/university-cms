package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class AdminNotFoundException extends EntityNotFoundException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
