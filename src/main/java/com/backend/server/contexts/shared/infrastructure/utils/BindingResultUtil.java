package com.backend.server.contexts.shared.infrastructure.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindingResultUtil {
    public static Map<String, Object> create(BindingResult result) {
        Map<String, Object> body = new HashMap<>();
        body.put("dateTime:", LocalDateTime.now());

        List<String> errors = result
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put("errors", errors);
        return body;
    }
}
