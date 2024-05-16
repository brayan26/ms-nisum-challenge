package com.backend.server.app.controllers.users;

import com.backend.server.contexts.users.domain.dto.User;
import com.backend.server.contexts.users.infrastructure.services.UserServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserValidatorRestController {
    @Autowired
    private UserServiceHandler userServiceHandler;

    @PostMapping(path = "/users/validate", produces = {"application/json"})
    public ResponseEntity<?> run(@RequestBody User user) {
        userServiceHandler.validateUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok("User valid");
    }
}
