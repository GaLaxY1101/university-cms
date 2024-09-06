package com.foxminded.korniichyk.university.service.exception;

public class GroupAlreadyAssignedException extends RuntimeException {
    public GroupAlreadyAssignedException(String message) {
        super(message);
    }
}
