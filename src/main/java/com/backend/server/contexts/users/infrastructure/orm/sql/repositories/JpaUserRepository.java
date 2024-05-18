package com.backend.server.contexts.users.infrastructure.orm.sql.repositories;

import com.backend.server.contexts.users.infrastructure.orm.sql.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllUsers();

    @Query("SELECT u FROM UserEntity u WHERE u.email=?1")
    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity u set u.isActive=false WHERE u.id=?1 ")
    void inactiveUser(String id);

    @Modifying
    @Query("UPDATE UserEntity u set u.lastLogin=?2 WHERE u.id=?1 ")
    void updateLastLogin(String id, Date now);

    @Modifying
    @Query("UPDATE UserEntity u set u.modified=?2 WHERE u.id=?1 ")
    void updateModifiedDate(String id, Date now);
}
