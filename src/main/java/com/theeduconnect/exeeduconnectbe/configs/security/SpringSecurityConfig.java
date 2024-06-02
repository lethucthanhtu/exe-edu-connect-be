package com.theeduconnect.exeeduconnectbe.configs.security;

import com.theeduconnect.exeeduconnectbe.constants.authentication.AuthenticationEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.authentication.AuthenticationRoles;
import com.theeduconnect.exeeduconnectbe.constants.certificate.CertificateEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.course.CourseEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.courseCategory.CourseCategoryEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.feedback.FeedbackEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.swagger.SwaggerEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.teacher.TeacherEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.OAuth2User;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.JwtAuthenticationFilterImpl;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.LoadOAuth2UserServiceImpl;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.RegisterOAuth2UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
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
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
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
                                        .requestMatchers(CourseEndpoints.JOIN_COURSE)
                                        .hasAnyAuthority(AuthenticationRoles.STUDENT)
                                        .requestMatchers(
                                                CourseEndpoints.GET_ALL_BY,
                                                CourseEndpoints.GET_BY_ID)
                                        .permitAll()
                                        .requestMatchers(
                                                TeacherEndpoints.ALLOWED_REQUEST_MATCHER_ENDPOINTS)
                                        .permitAll()
                                        .requestMatchers(
                                                FeedbackEndpoints.CREATE_COURSE_FEEDBACK_URL)
                                        .hasAnyAuthority(AuthenticationRoles.STUDENT)
                                        .requestMatchers(
                                                CourseCategoryEndpoints.GET_ALL_URL,
                                                CourseCategoryEndpoints.GET_BY_ID_URL)
                                        .permitAll()
                                        .requestMatchers(
                                                CertificateEndpoints.UPLOAD_CERTIFICATES_URL)
                                        .hasAnyAuthority(AuthenticationRoles.TEACHER)
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
                                        //
                                        .requestMatchers("/api/student-evaluations/**")
                                        .permitAll()
                                        .requestMatchers("/api/firebase/upload")
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
                                        int roleId = getRoleIdFromLoginUrl(request);
                                        OAuth2User oauthUser =
                                                (OAuth2User) authentication.getPrincipal();
                                        registerOAuth2UserServiceImpl.processOAuthPostLogin(
                                                oauthUser, roleId);
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

    private Integer getRoleIdFromLoginUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Integer) session.getAttribute("roleId");
    }
}
