package com.foxminded.korniichyk.university.service.exception;

public class LessonTypeNotFoundException extends RuntimeException {
    public LessonTypeNotFoundException(String message) {
        super(message);
    }
}
