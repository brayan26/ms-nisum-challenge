package com.backend.server.contexts.users.infrastructure.orm.mysql.repositories;

import com.backend.server.contexts.users.infrastructure.orm.mysql.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MySqlUserRepository extends CrudRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllUsers();

    @Query("SELECT u FROM UserEntity u WHERE u.username=?1")
    Optional<UserEntity> findByUsername(String username);
}
