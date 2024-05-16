package com.backend.server.contexts.shared.domain.interfaces;

import com.backend.server.contexts.shared.domain.events.DomainEvent;

public interface AggregateRoot {
    DomainEvent pullDomainEvent();
}
