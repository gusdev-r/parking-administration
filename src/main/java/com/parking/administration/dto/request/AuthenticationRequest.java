package com.parking.administration.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
