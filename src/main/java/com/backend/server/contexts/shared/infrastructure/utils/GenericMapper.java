package com.backend.server.contexts.shared.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class GenericMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(GenericMapper.class);

    public static <T> String serialize(T object) {
        String data = null;
        try {
            data = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error en el proceso de serializacion. Detalles: {}", e.getMessage());
        }
        return data;
    }

    public static <T> T deserialize(String json, Class<T> type) {
        T data = null;
        try {
            data = mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error("Error en el proceso de deserializacion. Detalles: {}", e.getMessage());
        }
        return data;
    }
}
