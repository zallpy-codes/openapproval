package com.zallpy.openapproval.approval.enums;

/**
 * Source used to resolve approvers.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalAssignmentSource {

    STATIC,

    DATABASE,

    DIRECTORY,

    LDAP,

    ACTIVE_DIRECTORY,

    HR_SYSTEM,

    WORKFLOW_CONTEXT,

    EXPRESSION,

    SERVICE,

    API
}