package com.foxminded.korniichyk.university.service.exception;

public class DisciplineAlreadyAssignedException extends RuntimeException {

    public DisciplineAlreadyAssignedException(String message) {
        super(message);
    }
}
