package com.backend.server.contexts.users.infrastructure.services;

import com.backend.server.contexts.shared.domain.errors.UsersError;
import com.backend.server.contexts.shared.domain.exceptions.GenericBadRequestException;
import com.backend.server.contexts.users.application.create.UserCreator;
import com.backend.server.contexts.users.application.find.UserValidator;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.dto.AccessTokenSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceHandler {
    @Autowired
    private UserCreator userCreatorUseCase;
    @Autowired
    private UserValidator userValidatorUseCase;
    @Autowired
    private PasswordEncoder encoder;

    public User create(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setIsActive(true);
        return this.userCreatorUseCase.run(user);
    }

    public AccessTokenSerializer doLogin(String email, String password) {
        User user = userValidatorUseCase.run(email);
        if (!encoder.matches(password, user.getPassword())) {
            throw new GenericBadRequestException(
                String.format("<UserServiceHandler - doLogin> password for the email '%s' does not match", email),
                UsersError.create().invalidUser().build()
            );
        }
        return AccessTokenSerializer.create(user.getToken());
    }
}
