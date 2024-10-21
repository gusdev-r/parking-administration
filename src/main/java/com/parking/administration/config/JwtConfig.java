package com.parking.administration.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class JwtConfig {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
}

