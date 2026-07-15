package com.zallpy.openapproval.approval.event;

/**
 * Publishes approval domain events.
 *
 * <p>
 * The approval engine depends only on this abstraction,
 * allowing different event transports such as Spring,
 * Kafka or RabbitMQ.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalDomainEventPublisher {

    /**
     * Publishes a domain event.
     *
     * @param event domain event
     */
    void publish(ApprovalDomainEvent event);

}