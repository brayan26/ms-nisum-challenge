package com.backend.server.contexts.users.application.find;

import com.backend.server.contexts.users.domain.dto.User;
import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    @Autowired
    private IUserRepository repository;

    public User run(String username) {
        return repository.validateUser(username);
    }
}
