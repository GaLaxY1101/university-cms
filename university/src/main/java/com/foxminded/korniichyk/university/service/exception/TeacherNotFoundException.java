package com.foxminded.korniichyk.university.service.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
