package org.bori.controllers.handlers.exceptions.model.authentication;

import org.springframework.http.HttpStatus;
import org.bori.controllers.handlers.exceptions.model.CustomException;

import java.util.ArrayList;

public class DuplicateUsernameException extends CustomException {
    private static final String MESSAGE_FORMAT = "The username %s is taken!";
    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public DuplicateUsernameException(String resource, String userName) {
        super(String.format(MESSAGE_FORMAT, userName), httpStatus, resource, new ArrayList<>());
    }
}
