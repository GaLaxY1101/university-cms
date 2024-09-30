package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class TeacherNotFoundException extends EntityNotFoundException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
