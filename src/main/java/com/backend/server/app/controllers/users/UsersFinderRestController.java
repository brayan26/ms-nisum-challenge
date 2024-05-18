package com.backend.server.app.controllers.users;

import com.backend.server.contexts.users.application.find.UsersFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersFinderRestController {
    @Autowired
    private UsersFinder usersFinderUseCase;

    @GetMapping(path = "/users/findAll", produces = {"application/json"})
    public ResponseEntity<?> run() {
        return ResponseEntity.ok(usersFinderUseCase.run());
    }
}
