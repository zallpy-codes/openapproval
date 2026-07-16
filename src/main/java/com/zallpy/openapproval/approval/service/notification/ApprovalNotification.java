package com.zallpy.openapproval.approval.service.notification;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Represents a notification ready for dispatch.
 *
 * <p>
 * This object is produced after recipient resolution and
 * template resolution have completed.
 * </p>
 *
 * <p>
 * It is intentionally transport-oriented and independent
 * of any delivery channel.
 * * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalNotification {

    /**
     * Notification recipients.
     */
    private final Collection<String> recipients =
            new LinkedHashSet<>();

    /**
     * Notification subject.
     */
    private String subject;

    /**
     * Notification body.
     */
    private String body;

    /**
     * Delivery channel.
     */
    private ApprovalNotificationChannel channel =
            ApprovalNotificationChannel.EMAIL;

    /**
     * Template variables.
     */
    private final Map<String, Object> variables =
            new LinkedHashMap<>();

    public Collection<String> getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(
            final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(
            final String body) {
        this.body = body;
    }

    public ApprovalNotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(
            final ApprovalNotificationChannel channel) {

        if (channel != null) {
            this.channel = channel;
        }
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

}