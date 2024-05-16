package com.backend.server.app.controllers.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class MicroserviceInformationGetter {
    @Autowired
    private Environment env;

    @GetMapping(path = "/info", produces = {"application/json"})
    public ResponseEntity<?> run() {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("msvc", env.getProperty("app.name"));
        body.put("version", env.getProperty("app.version"));
        return ResponseEntity.ok(body);
    }
}
