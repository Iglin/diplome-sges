package ru.ssk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 23.05.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MultipleRepresentationsException extends RuntimeException {
    private MultipleRepresentationsException() {
    }

    public MultipleRepresentationsException(String message) {
        super(message);
    }
}
