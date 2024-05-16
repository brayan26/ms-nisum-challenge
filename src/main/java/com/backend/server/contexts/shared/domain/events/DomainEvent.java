package com.backend.server.contexts.shared.domain.events;

import lombok.Data;

import java.util.Date;

@Data
public abstract class DomainEvent {
    private final String eventId;
    private final String eventName;
    private final Date occurredOn;

    public DomainEvent(String eventId, String eventName, Date occurredOn) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.occurredOn = occurredOn;
    }
}
