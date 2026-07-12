package com.zallpy.openapproval.config;

import com.zallpy.openapproval.common.security.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

/**
 * Configures Spring Data JPA auditing for OpenApproval.
 *
 * <p>
 * Enables automatic population of:
 * <ul>
 *     <li>createdAt</li>
 *     <li>createdBy</li>
 *     <li>updatedAt</li>
 *     <li>updatedBy</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

    /**
     * Auditor provider used by Spring Data JPA.
     *
     * @return AuditorAware implementation
     */
    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}