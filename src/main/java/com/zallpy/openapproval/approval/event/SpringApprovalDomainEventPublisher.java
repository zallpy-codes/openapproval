package com.zallpy.openapproval.approval.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Spring implementation of the domain event publisher.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SpringApprovalDomainEventPublisher
        implements ApprovalDomainEventPublisher {

    /**
     * Spring application event publisher.
     */
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final ApprovalDomainEvent event) {

        applicationEventPublisher.publishEvent(event);
    }

}