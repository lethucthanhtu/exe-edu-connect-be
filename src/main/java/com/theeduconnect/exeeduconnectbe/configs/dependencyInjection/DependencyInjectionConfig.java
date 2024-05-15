package com.theeduconnect.exeeduconnectbe.configs.dependencyInjection;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.theeduconnect.exeeduconnectbe.repositories")
public class DependencyInjectionConfig {}
