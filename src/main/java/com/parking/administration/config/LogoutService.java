package com.parking.administration.config;

import com.parking.administration.domain.token.Token;
import com.parking.administration.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token tknStorage = tokenRepo.findByToken(jwt).orElse(null);
        if(tknStorage != null) {
            tknStorage.setExpired(true);
            tknStorage.setRevoked(true);
            tokenRepo.save(tknStorage);
        }
    }
}
