package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class DisciplineNotFoundException extends EntityNotFoundException {
    public DisciplineNotFoundException(String message) {
        super(message);
    }
}
