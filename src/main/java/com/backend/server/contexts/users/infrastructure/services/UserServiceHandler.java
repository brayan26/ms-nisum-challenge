package com.backend.server.contexts.users.infrastructure.services;

import com.backend.server.contexts.shared.domain.errors.UsersError;
import com.backend.server.contexts.shared.domain.exceptions.GenericBadRequestException;
import com.backend.server.contexts.shared.infrastructure.services.KafkaEventHandlerService;
import com.backend.server.contexts.users.application.create.UserCreator;
import com.backend.server.contexts.users.application.find.UserValidator;
import com.backend.server.contexts.users.application.find.UsersFinder;
import com.backend.server.contexts.users.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceHandler {
    @Autowired
    private UserCreator userCreatorUseCase;
    @Autowired
    private UserValidator userValidatorUseCase;
    @Autowired
    private UsersFinder usersFinderUseCase;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private KafkaEventHandlerService eventBus;
    @Autowired
    private Environment env;

    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = this.userCreatorUseCase.run(user);
        this.eventBus.publisher(newUser.pullDomainEvent());
        return newUser;
    }

    public void validateUser(String username, String password) {
        User user = userValidatorUseCase.run(username);
        if (!encoder.matches(password, user.getPassword())) {
            throw new GenericBadRequestException(
                    String.format("<UserServiceHandler - validateUser> password for the username '%s' does not match", username),
                    UsersError.create().invalidUser().build());
        }
    }
}
