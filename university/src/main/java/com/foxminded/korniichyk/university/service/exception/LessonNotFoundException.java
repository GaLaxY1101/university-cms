package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class LessonNotFoundException extends EntityNotFoundException {
    public LessonNotFoundException(String message) {
        super(message);
    }
}
