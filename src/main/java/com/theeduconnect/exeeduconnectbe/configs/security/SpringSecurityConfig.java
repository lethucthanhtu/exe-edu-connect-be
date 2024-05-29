package com.theeduconnect.exeeduconnectbe.configs.security;

import com.theeduconnect.exeeduconnectbe.constants.authentication.endpoints.AuthenticationEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.authentication.roles.AuthenticationRoles;
import com.theeduconnect.exeeduconnectbe.constants.course.endpoints.CourseEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.courseCategory.endpoints.CourseCategoryEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.feedback.endpoints.FeedbackEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.swagger.SwaggerEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.teacher.endpoints.TeacherEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.OAuth2User;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.JwtAuthenticationFilterImpl;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.LoadOAuth2UserServiceImpl;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.RegisterOAuth2UserServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilterImpl jwtAuthenticationFilterImpl;
    private final LoadOAuth2UserServiceImpl loadOAuth2UserServiceImpl;
    private final RegisterOAuth2UserServiceImpl registerOAuth2UserServiceImpl;

    @Value("${educonnect.fe.url}")
    private String eduConnectFEUrl;

    public SpringSecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilterImpl jwtAuthenticationFilterImpl,
            LoadOAuth2UserServiceImpl loadOAuth2UserServiceImpl,
            RegisterOAuth2UserServiceImpl registerOAuth2UserServiceImpl) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilterImpl = jwtAuthenticationFilterImpl;
        this.loadOAuth2UserServiceImpl = loadOAuth2UserServiceImpl;
        this.registerOAuth2UserServiceImpl = registerOAuth2UserServiceImpl;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(eduConnectFEUrl, "http://127.0.0.1:5173"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(
                                                AuthenticationEndpoints
                                                        .ALLOWED_REQUEST_MATCHER_ENDPOINTS)
                                        .permitAll()
                                        .requestMatchers(
                                                SwaggerEndpoints.ALLOWED_REQUEST_MATCHER_ENDPOINTS)
                                        .permitAll()
                                        .requestMatchers(CourseEndpoints.CREATE)
                                        .hasAnyAuthority(AuthenticationRoles.TEACHER)
                                        .requestMatchers(CourseEndpoints.GET_ALL_BY,CourseEndpoints.GET_BY_ID)
                                        .permitAll()
                                        .requestMatchers(
                                                TeacherEndpoints.ALLOWED_REQUEST_MATCHER_ENDPOINTS)
                                        .permitAll()
                                        .requestMatchers(
                                                FeedbackEndpoints.CREATE_COURSE_FEEDBACK_URL)
                                        .hasAnyAuthority(AuthenticationRoles.STUDENT)
                                        .requestMatchers(CourseCategoryEndpoints.GET_ALL_URL)
                                        .permitAll()
                                        //                                        comment users
                                        .requestMatchers("/api/users")
                                        .permitAll()
                                        .requestMatchers("/api/users/{userId}")
                                        .permitAll()
                                        .requestMatchers("api/users/{userId}/change-password")
                                        .permitAll()
                                        //                                        Send mail
                                        .requestMatchers("api/sendmail")
                                        .permitAll()
                                        .requestMatchers("api/users/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .oauth2Login(
                        oauth2Login -> {
                            oauth2Login.userInfoEndpoint(
                                    userInfoEndpoint ->
                                            userInfoEndpoint.userService(
                                                    loadOAuth2UserServiceImpl));
                            oauth2Login.successHandler(
                                    (request, response, authentication) -> {
                                        OAuth2User oauthUser =
                                                (OAuth2User) authentication.getPrincipal();

                                        registerOAuth2UserServiceImpl.processOAuthPostLogin(
                                                oauthUser);

                                        response.sendRedirect(eduConnectFEUrl);
                                    });
                            oauth2Login.failureHandler(
                                    (request, response, authentication) -> {
                                        response.sendRedirect(eduConnectFEUrl);
                                    });
                        })
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(
                        exceptionHandling ->
                                exceptionHandling.authenticationEntryPoint(
                                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(
                        jwtAuthenticationFilterImpl, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
