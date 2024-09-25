package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
