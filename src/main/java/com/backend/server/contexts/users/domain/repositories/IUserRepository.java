package com.backend.server.contexts.users.domain.repositories;

import com.backend.server.contexts.users.domain.dto.User;

import java.util.List;

public interface IUserRepository {
    User create(User user);
    List<User> find();
    void delete(Long id);
    User validateUser(String username);
}
