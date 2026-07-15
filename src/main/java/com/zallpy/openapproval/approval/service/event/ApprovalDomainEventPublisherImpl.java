package com.zallpy.openapproval.approval.service.event;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;
import com.zallpy.openapproval.approval.event.ApprovalDomainEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Spring implementation of {@link ApprovalDomainEventPublisher}.
 *
 * <p>
 * Delegates approval domain events to Spring's event infrastructure,
 * allowing listeners to react without coupling to the approval engine.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ApprovalDomainEventPublisherImpl
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

        if (event == null) {
            return;
        }

        applicationEventPublisher.publishEvent(event);
    }

}