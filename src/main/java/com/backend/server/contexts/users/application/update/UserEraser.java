package com.backend.server.contexts.users.application.update;

import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEraser {
    @Autowired
    private IUserRepository repository;

    public void run(Long id) {
        repository.delete(id);
    }
}
