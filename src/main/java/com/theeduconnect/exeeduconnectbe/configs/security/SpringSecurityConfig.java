package com.theeduconnect.exeeduconnectbe.configs.security;

import com.theeduconnect.exeeduconnectbe.constants.authentication.endpoints.AuthenticationEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.authentication.roles.AuthenticationRoles;
import com.theeduconnect.exeeduconnectbe.constants.course.endpoints.CourseEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.swagger.SwaggerEndpoints;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${educonnect.fe.url}")
    private String eduConnectFEUrl;

    public SpringSecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //        http.cors(cors -> cors.configurationSource(request->new
        // CorsConfiguration().applyPermitDefaultValues()))
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(AuthenticationEndpoints.BASE_URL + "/**")
                                        .permitAll()
                                        .requestMatchers(
                                                SwaggerEndpoints.BASE_URL + "/**",
                                                SwaggerEndpoints.DOCS_URL + "/**")
                                        .permitAll()
                                        .requestMatchers(CourseEndpoints.CREATE)
                                        .hasAnyAuthority(AuthenticationRoles.TEACHER)
                                        .requestMatchers(CourseEndpoints.GET_ALL)
                                        .hasAnyAuthority(
                                                AuthenticationRoles.TEACHER,
                                                AuthenticationRoles.STUDENT)
                                        .anyRequest()
                                        .authenticated())
                //                .oauth2ResourceServer(oauth2 ->
                // oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowedOrigins(Arrays.asList(eduConnectFEUrl));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
