package com.foxminded.korniichyk.university.service.exception;

public class LessonAlreadyAssignedException extends RuntimeException {
    public LessonAlreadyAssignedException(String message) {
        super(message);
    }
}
