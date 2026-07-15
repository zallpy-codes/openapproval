package com.zallpy.openapproval.approval.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base contract for all approval domain events.
 *
 * <p>
 * Domain events represent immutable business facts emitted by the
 * approval engine whenever significant state transitions occur.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalDomainEvent {

    /**
     * Returns the unique event identifier.
     *
     * @return event identifier
     */
    UUID getEventId();

    /**
     * Returns the event occurrence timestamp.
     *
     * @return occurred at
     */
    LocalDateTime getOccurredAt();

}