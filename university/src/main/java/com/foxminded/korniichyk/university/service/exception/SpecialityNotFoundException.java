package com.foxminded.korniichyk.university.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class SpecialityNotFoundException extends EntityNotFoundException {
    public SpecialityNotFoundException(String message) {
        super(message);
    }
}
