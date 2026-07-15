package com.zallpy.openapproval.approval.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base implementation for approval domain events.
 *
 * <p>
 * Provides immutable event identity and timestamp.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public abstract class AbstractApprovalDomainEvent
        implements ApprovalDomainEvent {

    /**
     * Unique event identifier.
     */
    private final UUID eventId;

    /**
     * Event occurrence time.
     */
    private final LocalDateTime occurredAt;

    /**
     * Creates a new domain event.
     */
    protected AbstractApprovalDomainEvent() {

        this.eventId = UUID.randomUUID();
        this.occurredAt = LocalDateTime.now();
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

}