package com.backend.server.contexts.users.domain.dto;

import com.backend.server.contexts.shared.domain.events.DomainEvent;
import com.backend.server.contexts.shared.domain.interfaces.AggregateRoot;
import com.backend.server.contexts.users.domain.events.UserCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Data
public class User implements AggregateRoot {
    private Long id;
    private String name;
    private String username;
    private String password;

    public static User create(Long id, String name, String username, String password) {
        return new User(id, name, username, password);
    }

    @Override
    public UserCreatedEvent pullDomainEvent() {
        return UserCreatedEvent.create(
            UUID.randomUUID().toString(),
            this,
            new Date()
        );
    }
}
