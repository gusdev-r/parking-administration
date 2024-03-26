package com.parking.administration.demo.service;

import com.parking.administration.demo.domain.token.ConfirmationToken;
import com.parking.administration.demo.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    private ConfirmationTokenRepository tokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    public ConfirmationTokenService() {
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }
    public void setConfirmedAt(String token) {
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
