package org.bori.controllers.handlers.exceptions.model.authentication;

import org.springframework.http.HttpStatus;
import org.bori.controllers.handlers.exceptions.model.CustomException;

import java.util.ArrayList;

public class AuthenticationRequiredException extends CustomException {
    private static final String MESSAGE = "To access this functionality, you must first " +
            "log in!";
    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public AuthenticationRequiredException(String resource, String userName) {
        super(MESSAGE, httpStatus, resource, new ArrayList<>());
    }
}
