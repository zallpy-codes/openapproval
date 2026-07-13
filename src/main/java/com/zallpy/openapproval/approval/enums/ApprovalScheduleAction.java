package com.zallpy.openapproval.approval.enums;

/**
 * Defines the lifecycle action executed by the scheduler.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalScheduleAction {

    /**
     * Publish a policy version.
     */
    PUBLISH_VERSION,

    /**
     * Activate a policy version.
     */
    ACTIVATE_VERSION,

    /**
     * Deactivate a policy version.
     */
    DEACTIVATE_VERSION,

    /**
     * Retire a policy version.
     */
    RETIRE_VERSION,

    /**
     * Archive a policy version.
     */
    ARCHIVE_VERSION
}