package com.backend.server.contexts.users.domain.events;

import com.backend.server.contexts.shared.domain.events.DomainEvent;
import com.backend.server.contexts.users.domain.dto.User;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserCreatedEvent extends DomainEvent {
    //should refer to the event I'm launching
    public static final String EVENT_NAME = "ms.spring.template.topic.in";
    private final User payload;

    public UserCreatedEvent(String eventId, User payload, Date occurredOn) {
        super(eventId, EVENT_NAME, occurredOn);
        this.payload = payload;
    }

    public static UserCreatedEvent create(String eventId, User payload, Date occurredOn) {
        return new UserCreatedEvent(eventId, payload, occurredOn);
    }

}
