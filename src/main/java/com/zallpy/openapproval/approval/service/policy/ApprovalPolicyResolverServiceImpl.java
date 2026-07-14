package com.zallpy.openapproval.approval.service.policy;

import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.service.validation.ApprovalValidationService;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalPolicyResolverService}.
 *
 * <p>
 * Responsible for locating and validating approval policies before
 * workflow creation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalPolicyResolverServiceImpl
        implements ApprovalPolicyResolverService {

    private final ApprovalPolicyRepository approvalPolicyRepository;
    private final ApprovalValidationService approvalValidationService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalPolicy resolve(final String policyCode) {

        if (policyCode == null || policyCode.isBlank()) {
            throw new ApprovalValidationException(
                    "Approval policy code is required."
            );
        }

        ApprovalPolicy policy = approvalPolicyRepository
                .findByPolicyCode(policyCode)
                .orElseThrow(() ->
                        new ApprovalValidationException(
                                "Approval policy [" + policyCode + "] was not found."
                        ));

        approvalValidationService.validatePolicy(policy);

        return policy;
    }

}