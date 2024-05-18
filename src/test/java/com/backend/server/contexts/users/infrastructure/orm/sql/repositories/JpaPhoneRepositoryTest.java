package com.backend.server.contexts.users.infrastructure.orm.sql.repositories;

import com.backend.server.contexts.users.domain.PhoneMother;
import com.backend.server.contexts.users.domain.UserMother;
import com.backend.server.contexts.users.infrastructure.orm.sql.entities.PhoneEntity;
import com.backend.server.contexts.users.infrastructure.orm.sql.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class JpaPhoneRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(JpaPhoneRepositoryTest.class);
    @Autowired
    private JpaPhoneRepository repository;

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void add_phone_numbers() {
        UserEntity userEntity = userRepository.findByEmail(UserMother.random().getEmail()).orElse(null);

        assertThat(userEntity).isNotNull();

        PhoneEntity phoneEntity = PhoneEntity.create(
                PhoneMother.random().getNumber(),
                PhoneMother.random().getCityCode(),
                PhoneMother.random().getCountryCode(),
                userEntity.getId()
        );

        PhoneEntity createdPhone = repository.save(phoneEntity);
        assertThat(createdPhone).isNotNull();

        log.info("users: {}", userRepository.findByEmail(UserMother.random().getEmail()));
    }
}
