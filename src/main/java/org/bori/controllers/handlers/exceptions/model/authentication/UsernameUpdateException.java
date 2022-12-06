package org.bori.controllers.handlers.exceptions.model.authentication;

import org.springframework.http.HttpStatus;
import org.bori.controllers.handlers.exceptions.model.CustomException;

import java.util.ArrayList;

public class UsernameUpdateException extends CustomException {
    private static final String MESSAGE = "You can't edit one's username, because it's a lifelong unique identifier";
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public UsernameUpdateException(String resource) {
        super(MESSAGE, httpStatus, resource, new ArrayList<>());
    }
}
