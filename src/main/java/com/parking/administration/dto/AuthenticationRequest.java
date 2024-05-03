package com.parking.administration.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}
