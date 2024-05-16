package com.backend.server.app.controllers.users;

import com.backend.server.contexts.users.application.update.UserEraser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserEraserRestController {
    @Autowired
    private UserEraser userEraserUseCase;

    @DeleteMapping(path = "/users/delete/{id}", produces = {"application/json"})
    public ResponseEntity<?> run(@PathVariable Long id) {
        userEraserUseCase.run(id);
        return ResponseEntity.accepted().build();
    }
}
