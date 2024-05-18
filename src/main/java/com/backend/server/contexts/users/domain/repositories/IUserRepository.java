package com.backend.server.contexts.users.domain.repositories;

import com.backend.server.contexts.users.domain.clazz.User;

import java.util.List;

public interface IUserRepository {
    User create(User user);
    List<User> find();
    void delete(String id);
    User doLogin(String username);
}
