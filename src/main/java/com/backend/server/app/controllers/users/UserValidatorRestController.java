package com.backend.server.app.controllers.users;

import com.backend.server.contexts.shared.infrastructure.utils.BindingResultUtil;
import com.backend.server.contexts.users.domain.clazz.Login;
import com.backend.server.contexts.users.infrastructure.services.UserServiceHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserValidatorRestController {
    @Autowired
    private UserServiceHandler userServiceHandler;

    @PostMapping(path = "/doLogin", produces = {"application/json"})
    public ResponseEntity<?> run(@Valid @RequestBody Login user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(BindingResultUtil.create(result));
        }
        return ResponseEntity.ok(userServiceHandler.doLogin(user.getEmail(), user.getPassword()));
    }
}
