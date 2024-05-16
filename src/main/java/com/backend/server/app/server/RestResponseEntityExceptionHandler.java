package com.backend.server.app.server;

import com.backend.server.contexts.shared.domain.exceptions.GenericBadRequestException;
import com.backend.server.contexts.shared.domain.exceptions.GenericNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(GenericNotFoundException.class)
    public ResponseEntity<?> notFoundError(GenericNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericBadRequestException.class)
    public ResponseEntity<?> badRequestError(GenericBadRequestException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
    }

}
