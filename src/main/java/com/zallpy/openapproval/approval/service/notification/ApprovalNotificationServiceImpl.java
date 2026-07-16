package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalNotificationService}.
 *
 * <p>
 * Coordinates notification processing for approval domain events.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Resolve notification recipients</li>
 * <li>Resolve notification templates</li>
 * <li>Delegate delivery to the notification dispatcher</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalNotificationServiceImpl
        implements ApprovalNotificationService {

    /**
     * Resolves notification recipients.
     */
    private final ApprovalNotificationRecipientResolver recipientResolver;

    /**
     * Resolves notification templates.
     */
    private final ApprovalNotificationTemplateResolver templateResolver;

    /**
     * Dispatches notifications.
     */
    private final ApprovalNotificationDispatcher dispatcher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(
            final ApprovalDomainEvent event) {

        if (event == null) {
            return;
        }

        ApprovalNotification notification = templateResolver.resolve(event);

        notification.getRecipients().addAll(
                recipientResolver.resolveRecipients(event));

        dispatcher.dispatch(notification);
    }

}