package com.theeduconnect.exeeduconnectbe.features.authentication.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
    //    Boolean validateToken(String token, UserDetails userDetails);
}
