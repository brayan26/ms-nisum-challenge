package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.infrastructure.utils.BindingResultUtil;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.infrastructure.services.UserServiceHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserCreatorRestController {
    @Autowired
    private UserServiceHandler userServiceHandler;

    @PostMapping(path = "/users/add", produces = {"application/json"})
    public ResponseEntity<?> run(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(BindingResultUtil.create(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceHandler.create(user).serialize());
    }
}
