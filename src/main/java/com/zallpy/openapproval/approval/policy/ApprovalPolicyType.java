package com.zallpy.openapproval.approval.policy;

/**
 * Defines how an approval policy is assigned within the platform.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPolicyType {

    /**
     * Policy is available globally across all registered applications.
     */
    GLOBAL,

    /**
     * Policy belongs to a specific application.
     */
    APPLICATION,

    /**
     * Policy is owned by a specific business module within an application.
     */
    MODULE,

    /**
     * Policy is intended for testing, simulation or development purposes.
     */
    SANDBOX

}