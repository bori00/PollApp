package org.bori.controllers.handlers.exceptions.model.authentication;

import org.bori.controllers.handlers.exceptions.model.CustomException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class InvalidLoginException extends CustomException {
    private static final String MESSAGE = "Login failed. No user with these credentials found";
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public InvalidLoginException(String resource) {
        super(MESSAGE, httpStatus, resource, new ArrayList<>());
    }
}
