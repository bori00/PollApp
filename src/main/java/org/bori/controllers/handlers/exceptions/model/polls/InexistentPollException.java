package org.bori.controllers.handlers.exceptions.model.polls;

import org.bori.controllers.handlers.exceptions.model.CustomException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class InexistentPollException extends CustomException {
    private static final String MESSAGE_FORMAT = "The requested poll with code %d does not exist." +
            " Please verify that the code that you provided is correct";
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public InexistentPollException(String resource, Long code) {
        super(String.format(MESSAGE_FORMAT, code), httpStatus, resource, new ArrayList<>());
    }
}
