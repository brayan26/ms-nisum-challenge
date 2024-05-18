package com.backend.server.contexts.shared.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GenericUnauthorizedException extends RuntimeException {
    private Object error;
    public GenericUnauthorizedException(String message, Object error) {
        super(message);
        this.error = error;
    }
}

