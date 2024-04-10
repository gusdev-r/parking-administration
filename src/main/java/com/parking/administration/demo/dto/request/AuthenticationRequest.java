package com.parking.administration.demo.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
