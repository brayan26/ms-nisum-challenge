package com.backend.server.contexts.users.application.create;

import com.backend.server.contexts.users.domain.dto.User;
import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreator {
    @Autowired
    private IUserRepository repository;

    public User run(User user) {
        return repository.create(user);
    }
}
