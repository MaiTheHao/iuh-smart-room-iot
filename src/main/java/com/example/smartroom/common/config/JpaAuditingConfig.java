package com.example.smartroom.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Config này để bật tính năng Auditing (tự động điền timestamp).
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    
}
