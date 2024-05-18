package com.backend.server.contexts.users.infrastructure.orm.sql.repositories;

import com.backend.server.contexts.users.domain.PhoneMother;
import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.infrastructure.orm.sql.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class JpaUserRepositoryTest {
    @Autowired
    private JpaUserRepository repository;

    @Test
    @Transactional
    @Rollback
    public void create_user_db() {
        User user = UserMother.random();
        UserEntity entity = UserEntity.create(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            List.of(PhoneMother.random()),
            user.getIsActive(),
            user.getToken()
        );

        UserEntity createdEntity = repository.save(entity);
        assertThat(createdEntity).isNotNull();
        assertThat(createdEntity.getId()).isEqualTo(UserMother.random().getId());
    }

    @Test
    @Transactional(readOnly = true)
    public void find_all_users_db() {
        List<UserEntity> entities = repository.findAllUsers();
        assertThat(entities).isNotNull();
        assertThat(entities.size()).isGreaterThan(0);
    }

    @Test
    @Transactional(readOnly = true)
    public void find_users_by_email() {
        Optional<UserEntity> entity = repository.findByEmail(UserMother.random().getEmail());
        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getEmail()).isEqualTo(UserMother.random().getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void inactivate_user() {
        repository.inactiveUser(UserMother.random().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void update_last_login() {
        repository.updateLastLogin(UserMother.random().getId(), new Date());
    }

}
