package com.backend.server.contexts.users.infrastructure.repositories;

import com.backend.server.contexts.shared.domain.errors.UsersError;
import com.backend.server.contexts.shared.domain.exceptions.GenericBadRequestException;
import com.backend.server.contexts.shared.domain.exceptions.GenericNotFoundException;
import com.backend.server.contexts.users.domain.dto.User;
import com.backend.server.contexts.users.domain.repositories.IUserRepository;
import com.backend.server.contexts.users.infrastructure.orm.mysql.entities.UserEntity;
import com.backend.server.contexts.users.infrastructure.orm.mysql.repositories.MySqlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryMysql implements IUserRepository {
    @Autowired
    private MySqlUserRepository repository;
    
    @Override
    @Transactional
    public User create(User user) {
        Optional<UserEntity> optional = repository.findByUsername(user.getUsername());
        if (optional.isPresent()) {
            throw new GenericBadRequestException(
                    String.format("<UserRepositoryMysql - create> username %s already exists", user.getUsername()),
                    UsersError.create().alreadyExists().build());
        }

        UserEntity entity = repository.save(
                UserEntity.create(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getPassword()
                )
        );

        return User.create(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getPassword()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> find() {
        return repository.findAllUsers()
                .stream()
                .map(entity ->
                        User.create(entity.getId(), entity.getName(), entity.getUsername(), entity.getPassword()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<UserEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new GenericNotFoundException(
                    String.format("<UserRepositoryMysql - delete> userId %s does not exists", id),
                    UsersError.create().notFound().build());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User validateUser(String username) {
        Optional<UserEntity> optional = repository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new GenericNotFoundException(
                    String.format("<UserRepositoryMysql - validateUser> username '%s' not exists", username),
                    UsersError.create().notFound().build());
        }
        UserEntity entity = optional.get();
        return User.create(
            entity.getId(),
            entity.getName(),
            entity.getUsername(),
            entity.getPassword()
        );
    }
}
