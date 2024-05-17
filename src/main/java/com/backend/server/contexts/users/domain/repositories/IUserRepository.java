package com.backend.server.contexts.users.domain.repositories;

import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.UserSerializer;

import java.util.List;

public interface IUserRepository {
    User create(User user);
    List<UserSerializer> find();
    void delete(String id);
    User doLogin(String username);
}
