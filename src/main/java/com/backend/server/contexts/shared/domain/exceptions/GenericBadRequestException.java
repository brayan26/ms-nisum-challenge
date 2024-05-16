package com.backend.server.contexts.shared.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class GenericBadRequestException extends RuntimeException {
    private Object error;
    public GenericBadRequestException(String message, Object error) {
        super(message);
        this.error = error;
    }
}
