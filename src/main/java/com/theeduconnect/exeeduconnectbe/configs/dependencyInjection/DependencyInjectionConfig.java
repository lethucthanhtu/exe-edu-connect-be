package com.theeduconnect.exeeduconnectbe.configs.dependencyInjection;

import com.theeduconnect.exeeduconnectbe.configs.mappers.AuthenticationMapper;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.AuthenticationService;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.AuthenticationServiceImpl;
import com.theeduconnect.exeeduconnectbe.repositories.RoleRepository;
import com.theeduconnect.exeeduconnectbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.theeduconnect.exeeduconnectbe.repositories")
public class DependencyInjectionConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationMapper authenticationMapper;

    @Autowired
    public DependencyInjectionConfig(UserRepository userRepository, AuthenticationMapper authenticationMapper, RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.roleRepository = roleRepository;
    }

    @Bean
    public AuthenticationService GetAuthenticationService() {
        return new AuthenticationServiceImpl(userRepository, authenticationMapper, roleRepository);
    }
}
