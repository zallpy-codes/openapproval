package com.zallpy.openapproval.approval.enums;

/**
 * Represents the lifecycle status of an approval policy template.
 *
 * <p>
 * Policy templates progress through a controlled lifecycle before
 * they become available for use by tenants and organizations.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPolicyTemplateStatus {

    /**
     * Template is under development.
     */
    DRAFT,

    /**
     * Template has been published.
     */
    PUBLISHED,

    /**
     * Template is available for production use.
     */
    ACTIVE,

    /**
     * Template should no longer be used for new policies.
     */
    DEPRECATED,

    /**
     * Template has been permanently archived.
     */
    ARCHIVED
}