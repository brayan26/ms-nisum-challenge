package com.backend.server.contexts.users.application.find;

import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindUserByEmail {
    @Autowired
    private IUserRepository repository;

    public User run(String email) {
        return repository.findUserByEmail(email);
    }
}
