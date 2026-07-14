package com.zallpy.openapproval.approval.service.policy;

import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalPolicyRepository;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalPolicyResolver}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalPolicyResolverImpl
        implements ApprovalPolicyResolver {

    private final ApprovalPolicyRepository approvalPolicyRepository;

    @Override
    public UUID resolvePolicyId(final String policyCode) {

        ApprovalPolicy policy = approvalPolicyRepository
                .findByPolicyCode(policyCode)
                .orElseThrow(() ->
                        new ApprovalValidationException(
                                "Approval policy [" + policyCode + "] was not found."));

        return policy.getId();
    }

    @Override
    public boolean policyExists(final String policyCode) {
        return approvalPolicyRepository.existsByPolicyCode(policyCode);
    }

    @Override
    public boolean isPolicyActive(final String policyCode) {

        return approvalPolicyRepository
                .findByPolicyCode(policyCode)
                .map(ApprovalPolicy::isActive)
                .orElse(false);
    }

}