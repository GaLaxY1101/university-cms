package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class LessonTypeNotFoundException extends EntityNotFoundException {
    public LessonTypeNotFoundException(String message) {
        super(message);
    }
}
