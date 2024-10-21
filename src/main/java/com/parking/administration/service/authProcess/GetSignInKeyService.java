package com.parking.administration.service.authProcess;


import com.parking.administration.config.JwtConfig;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@RequiredArgsConstructor
@Service
public class GetSignInKeyService {
    private final JwtConfig jwtConfig;

    public Key execute() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }
}

