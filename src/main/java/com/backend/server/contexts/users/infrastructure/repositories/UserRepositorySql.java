package com.backend.server.contexts.users.infrastructure.repositories;

import com.backend.server.contexts.shared.domain.errors.UsersError;
import com.backend.server.contexts.shared.domain.exceptions.GenericBadRequestException;
import com.backend.server.contexts.shared.domain.exceptions.GenericNotFoundException;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import com.backend.server.contexts.users.infrastructure.orm.sql.entities.UserEntity;
import com.backend.server.contexts.users.infrastructure.orm.sql.repositories.JpaUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserRepositorySql implements IUserRepository {
    @Autowired
    private JpaUserRepository repository;

    @Autowired
    private Environment env;

    @Override
    @Transactional
    public User create(User user) {
        Optional<UserEntity> optional = repository.findByEmail(user.getEmail());
        if (optional.isPresent()) {
            throw new GenericBadRequestException(
                String.format("<UserRepositorySql - create> email %s already exists", user.getEmail()),
                UsersError.create().alreadyExists().build());
        }

        UserEntity entity = repository.save(
            UserEntity.create(
                user.getId(), user.getName(), user.getEmail(),
                user.getPassword(), user.getPhones(), user.getIsActive(),
                user.getToken()
            )
        );

        return User.create(
            entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword(),
            user.getPhones(), entity.getIsActive(), entity.getToken(),
            entity.getCreatedAt(), entity.getModified(), entity.getLastLogin()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> find() {
        return repository.findAllUsers()
                .stream()
                .map(entity -> new ModelMapper().map(entity, User.class))
                .toList();
    }

    @Override
    @Transactional
    public void delete(String id) {
        Optional<UserEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new GenericNotFoundException(
                    String.format("<UserRepositorySql - delete> userId %s does not exists", id),
                    UsersError.create().notFound().build());
        }
        repository.inactiveUser(id);
        ZoneId zoneId = ZoneId.of(Objects.requireNonNull(env.getProperty("app.timezone")));
        Date today = Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
        repository.updateModifiedDate(id, today);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        Optional<UserEntity> optional = repository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new GenericNotFoundException(
                    String.format("<UserRepositorySql - findUserByEmail> email '%s' not exists", email),
                    UsersError.create().notFound().build());
        }
        return new ModelMapper().map(optional.get(), User.class);
    }

    @Override
    @Transactional(readOnly = true)
    public User doLogin(String email) {
        Optional<UserEntity> optional = repository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new GenericNotFoundException(
                String.format("<UserRepositorySql - validateUser> email '%s' not exists", email),
                UsersError.create().notFound().build());
        }
        UserEntity entity = optional.get();

        ZoneId zoneId = ZoneId.of(Objects.requireNonNull(env.getProperty("app.timezone")));
        Date today = Date.from(LocalDateTime.now().atZone(zoneId).toInstant());
        repository.updateLastLogin(entity.getId(), today);
        
        User user = new User();
        user.setPassword(entity.getPassword());
        user.setToken(entity.getToken());
        
        return user;
    }
}
