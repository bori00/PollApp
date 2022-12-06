package org.bori.controllers.handlers.exceptions.model.authentication;

import org.springframework.http.HttpStatus;
import org.bori.controllers.handlers.exceptions.model.CustomException;

import java.util.ArrayList;

public class NoAccessToDataException extends CustomException {
    private static final String MESSAGE = "You don't have access to this data/functionality";
    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public NoAccessToDataException(String resource) {
        super(MESSAGE, httpStatus, resource, new ArrayList<>());
    }
}
