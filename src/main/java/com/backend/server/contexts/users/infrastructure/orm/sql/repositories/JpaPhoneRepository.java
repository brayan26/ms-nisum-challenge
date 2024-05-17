package com.backend.server.contexts.users.infrastructure.orm.sql.repositories;

import com.backend.server.contexts.users.infrastructure.orm.sql.entities.PhoneEntity;
import org.springframework.data.repository.CrudRepository;

public interface JpaPhoneRepository extends CrudRepository<PhoneEntity, Long> {
}
