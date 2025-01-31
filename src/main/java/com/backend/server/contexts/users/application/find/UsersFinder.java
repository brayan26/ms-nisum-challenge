package com.backend.server.contexts.users.application.find;

import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersFinder {
    @Autowired
    private IUserRepository repository;

    public List<User> run() {
        return repository.find();
    }
}
