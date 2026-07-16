package com.zallpy.openapproval.approval.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalNotificationDispatcher}.
 *
 * <p>
 * Responsible for dispatching notifications to the configured
 * delivery channel.
 * </p>
 *
 * <p>
 * V1 implementation simply logs notifications. Future milestones
 * will integrate Email, SMS, In-App notifications, Webhooks,
 * Microsoft Teams and Slack.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ApprovalNotificationDispatcherImpl
        implements ApprovalNotificationDispatcher {

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispatch(
            final ApprovalNotification notification) {

        if (notification == null) {
            return;
        }

        log.info(
                "Dispatching {} notification to {} recipient(s). Subject={}",
                notification.getChannel(),
                notification.getRecipients().size(),
                notification.getSubject());

        /*
         * Future milestones:
         *
         * switch (notification.getChannel()) {
         *
         *     case EMAIL ->
         *          emailNotificationSender.send(notification);
         *
         *     case SMS ->
         *          smsNotificationSender.send(notification);
         *
         *     case IN_APP ->
         *          inAppNotificationSender.send(notification);
         *
         *     case WEBHOOK ->
         *          webhookNotificationSender.send(notification);
         *
         *     case TEAMS ->
         *          teamsNotificationSender.send(notification);
         *
         *     case SLACK ->
         *          slackNotificationSender.send(notification);
         * }
         */
    }

}