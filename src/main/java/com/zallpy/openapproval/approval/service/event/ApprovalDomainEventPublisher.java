package com.zallpy.openapproval.approval.service.event;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

/**
 * Publishes approval domain events.
 *
 * <p>
 * This abstraction decouples the approval engine from the
 * underlying event publication mechanism.
 * </p>
 *
 * <p>
 * Implementations may publish events using Spring,
 * messaging infrastructure, or external event buses.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalDomainEventPublisher {

    /**
     * Publishes the supplied domain event.
     *
     * @param event domain event
     */
    void publish(ApprovalDomainEvent event);

}