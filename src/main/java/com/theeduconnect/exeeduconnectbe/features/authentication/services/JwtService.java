package com.theeduconnect.exeeduconnectbe.features.authentication.services;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(Map<String, Object> claims, UserDetails userDetails);

    int extractUserId(String token);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
    //    Boolean validateToken(String token, UserDetails userDetails);
}
