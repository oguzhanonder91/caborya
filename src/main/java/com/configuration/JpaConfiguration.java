package com.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by oguzhanonder - 16.10.2018
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("com.common.repository")
public class JpaConfiguration {
}
