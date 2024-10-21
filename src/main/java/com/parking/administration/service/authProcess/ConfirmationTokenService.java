package com.parking.administration.service;

import com.parking.administration.domain.token.ConfirmationToken;
import com.parking.administration.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository tokenRepository;

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
