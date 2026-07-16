package com.zallpy.openapproval.approval.timeline;

import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a single item within an approval timeline.
 *
 * <p>
 * Timeline items are presentation models derived from
 * {@code ApprovalAudit} records and are intended for
 * REST APIs, dashboards and workflow history screens.
 * </p>
 *
 * <p>
 * Every timeline item represents one significant event
 * in the lifecycle of an approval workflow.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalTimelineItem
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Audit record identifier.
     */
    private UUID auditId;

    /**
     * Time the event occurred.
     */
    private LocalDateTime occurredAt;

    /**
     * Timeline title.
     */
    private String title;

    /**
     * Human-readable description.
     */
    private String description;

    /**
     * Audit action.
     */
    private ApprovalAuditAction action;

    /**
     * User that performed the action.
     */
    private UUID actorId;

    /**
     * Display name of the actor.
     */
    private String actorName;

    /**
     * Actor type.
     */
    private ApprovalAuditActorType actorType;

    /**
     * UI icon.
     */
    private String icon;

    /**
     * UI color.
     */
    private String color;

    /**
     * Indicates whether the event was generated
     * automatically by the system.
     */
    private boolean systemGenerated;

    /**
     * Timeline sequence number.
     */
    private Integer sequence;

    /**
     * Optional UI badge.
     */
    private String badge;

    /**
     * Indicates whether this is the
     * currently active timeline item.
     */
    private boolean current;

    /**
     * Default constructor.
     */
    public ApprovalTimelineItem() {
    }

    /**
     * Returns the audit identifier.
     *
     * @return audit identifier
     */
    public UUID getAuditId() {
        return auditId;
    }

    /**
     * Sets the audit identifier.
     *
     * @param auditId audit identifier
     */
    public void setAuditId(
            final UUID auditId) {
        this.auditId = auditId;
    }

    /**
     * Returns the event timestamp.
     *
     * @return event timestamp
     */
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    /**
     * Sets the event timestamp.
     *
     * @param occurredAt event timestamp
     */
    public void setOccurredAt(
            final LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    /**
     * Returns the timeline title.
     *
     * @return timeline title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the timeline title.
     *
     * @param title timeline title
     */
    public void setTitle(
            final String title) {
        this.title = title;
    }

    /**
     * Returns the timeline description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the timeline description.
     *
     * @param description description
     */
    public void setDescription(
            final String description) {
        this.description = description;
    }

    /**
     * Returns the audit action.
     *
     * @return audit action
     */
    public ApprovalAuditAction getAction() {
        return action;
    }

    /**
     * Sets the audit action.
     *
     * @param action audit action
     */
    public void setAction(
            final ApprovalAuditAction action) {
        this.action = action;
    }

    /**
     * Returns the actor identifier.
     *
     * @return actor identifier
     */
    public UUID getActorId() {
        return actorId;
    }

    /**
     * Sets the actor identifier.
     *
     * @param actorId actor identifier
     */
    public void setActorId(
            final UUID actorId) {
        this.actorId = actorId;
    }

    /**
     * Returns the actor name.
     *
     * @return actor name
     */
    public String getActorName() {
        return actorName;
    }

    /**
     * Sets the actor name.
     *
     * @param actorName actor name
     */
    public void setActorName(
            final String actorName) {
        this.actorName = actorName;
    }

    /**
     * Returns the actor type.
     *
     * @return actor type
     */
    public ApprovalAuditActorType getActorType() {
        return actorType;
    }

    /**
     * Sets the actor type.
     *
     * @param actorType actor type
     */
    public void setActorType(
            final ApprovalAuditActorType actorType) {
        this.actorType = actorType;
    }

    /**
     * Returns the UI icon.
     *
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the UI icon.
     *
     * @param icon icon
     */
    public void setIcon(
            final String icon) {
        this.icon = icon;
    }

    /**
     * Returns the UI color.
     *
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the UI color.
     *
     * @param color color
     */
    public void setColor(
            final String color) {
        this.color = color;
    }

    /**
     * Indicates whether this event was
     * generated automatically.
     *
     * @return true if system generated
     */
    public boolean isSystemGenerated() {
        return systemGenerated;
    }

    /**
     * Sets whether this event was
     * generated automatically.
     *
     * @param systemGenerated system generated flag
     */
    public void setSystemGenerated(
            final boolean systemGenerated) {
        this.systemGenerated = systemGenerated;
    }

    /**
     * Returns the timeline sequence number.
     *
     * @return sequence number
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * Sets the timeline sequence number.
     *
     * @param sequence sequence number
     */
    public void setSequence(
            final Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * Returns the timeline badge.
     *
     * @return badge
     */
    public String getBadge() {
        return badge;
    }

    /**
     * Sets the timeline badge.
     *
     * @param badge badge
     */
    public void setBadge(
            final String badge) {
        this.badge = badge;
    }

    /**
     * Indicates whether this is the current
     * timeline item.
     *
     * @return true if current
     */
    public boolean isCurrent() {
        return current;
    }

    /**
     * Sets whether this is the current
     * timeline item.
     *
     * @param current current flag
     */
    public void setCurrent(
            final boolean current) {
        this.current = current;
    }

}