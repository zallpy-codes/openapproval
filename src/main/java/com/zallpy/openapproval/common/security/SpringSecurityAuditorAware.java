package com.zallpy.openapproval.common.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides the current authenticated user for JPA auditing.
 */
public class SpringSecurityAuditorAware implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        /*
         * Security module has not been implemented yet.
         *
         * Once UserPrincipal is introduced, replace this implementation
         * with:
         *
         * if (authentication.getPrincipal() instanceof UserPrincipal principal) {
         *     return Optional.of(principal.getId());
         * }
         */

        return Optional.empty();
    }
}