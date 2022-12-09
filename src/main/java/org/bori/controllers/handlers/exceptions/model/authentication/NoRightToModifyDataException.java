package org.bori.controllers.handlers.exceptions.model.authentication;

import org.bori.controllers.handlers.exceptions.model.CustomException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class NoRightToModifyDataException extends CustomException {
    private static final String MESSAGE = "You don't have the right to modify this data";
    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public NoRightToModifyDataException(String resource) {
        super(MESSAGE, httpStatus, resource, new ArrayList<>());
    }
}
