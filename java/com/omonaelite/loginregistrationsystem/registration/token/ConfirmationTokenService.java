package com.omonaelite.loginregistrationsystem.registration.token;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public record ConfirmationTokenService(
        ConfirmationTokenRepository tokenRepository) {
    public void saveConfirmationToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return tokenRepository.upDateConfirmAt(token, LocalDateTime.now());
    }
}
