package com.theeduconnect.exeeduconnectbe.configs.security;

import com.theeduconnect.exeeduconnectbe.constants.authentication.endpoints.AuthenticationEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.authentication.roles.AuthenticationRoles;
import com.theeduconnect.exeeduconnectbe.constants.course.endpoints.CourseEndpoints;
import com.theeduconnect.exeeduconnectbe.constants.swagger.SwaggerEndpoints;
import com.theeduconnect.exeeduconnectbe.features.authentication.dtos.CustomOAuth2User;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.CustomOAuth2UserServiceImpl;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.JwtAuthenticationFilterImpl;
import com.theeduconnect.exeeduconnectbe.features.authentication.services.impl.OAuth2UserServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilterImpl jwtAuthenticationFilterImpl;
    private final CustomOAuth2UserServiceImpl customOAuth2UserServiceImpl;
    private final OAuth2UserServiceImpl oAuth2UserServiceImpl;

    @Value("${educonnect.fe.url}")
    private String eduConnectFEUrl;

    public SpringSecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilterImpl jwtAuthenticationFilterImpl,
            CustomOAuth2UserServiceImpl customOAuth2UserServiceImpl,
            OAuth2UserServiceImpl oAuth2UserServiceImpl) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilterImpl = jwtAuthenticationFilterImpl;
        this.customOAuth2UserServiceImpl = customOAuth2UserServiceImpl;
        this.oAuth2UserServiceImpl = oAuth2UserServiceImpl;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
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
                                        .requestMatchers(CourseEndpoints.GET_ALL)
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
                                                    customOAuth2UserServiceImpl));
                            oauth2Login.successHandler(
                                    (request, response, authentication) -> {
                                        CustomOAuth2User oauthUser =
                                                (CustomOAuth2User) authentication.getPrincipal();

                                        oAuth2UserServiceImpl.processOAuthPostLogin(oauthUser);

                                        response.sendRedirect(eduConnectFEUrl);
                                    });
                        })
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilterImpl, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(eduConnectFEUrl));
        configuration.setAllowedHeaders(
                List.of("Authorization", "Content-Type", "Access-Control-Allow-Origin"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        //        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
